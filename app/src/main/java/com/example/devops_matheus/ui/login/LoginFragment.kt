package com.example.devops_matheus.ui.login

import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.management.ManagementException
import com.auth0.android.management.UsersAPIClient
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.auth0.android.result.UserProfile
import com.example.devops_matheus.R
import com.example.devops_matheus.ui.database.users.UserDatabase
import com.example.devops_matheus.ui.screens.profile.ProfileFragment
import com.example.devops_matheus.ui.screens.profile.ProfileViewModel
import com.example.devops_matheus.ui.screens.profile.ProfileViewModelFactory
import timber.log.Timber

class LoginFragment : Fragment() {
    private lateinit var account: Auth0
    private lateinit var loggedInText: TextView
    private var loggedIn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        // OAUTH
        account = Auth0(
            getString(R.string.com_auth0_clientID),
            getString(R.string.com_auth0_domain)
        )

        val appContext = requireNotNull(this.activity).application
        val dataSource = UserDatabase.getInstance(appContext).userDatabaseDao

        val viewModelFactory = LoginViewModelFactory(dataSource, appContext)
        val viewModel: LoginViewModel by viewModels{viewModelFactory}

        val loginButton = view.findViewById<Button>(R.id.loginbutton)
        loginButton?.setOnClickListener {
            loginWithBrowser(viewModel)
        }
        val logoutButton = view.findViewById<Button>(R.id.logout_button)
        logoutButton?.setOnClickListener {
            logout()
        }
        loggedInText = view.findViewById(R.id.logged_in_textview)

        checkIfToken(viewModel)
        setLoggedInText()

        return view
    }

    private fun checkIfToken(viewModel: LoginViewModel) {
        val token = CredentialsManager.getAccessToken(requireContext())
        if (token != null) {
            // checking if the token works...
            showUserProfile(token, viewModel)
        } else {
            Toast.makeText(context, "Token doesn't exist", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setLoggedInText() {
        if (loggedIn) {
            loggedInText.text = "you're logged in"
        } else {
            loggedInText.text = "not logged in"
        }
    }

    private fun loginWithBrowser(viewModel: LoginViewModel) {
        // Setup the WebAuthProvider, using the custom scheme and scope.

        WebAuthProvider.login(account)
            .withScheme("demo")
            .withScope("openid profile email")
            // Launch the authentication passing the callback where the results will be received
            .start(
                requireContext(),
                object : Callback<Credentials,
                        AuthenticationException> {
                    // Called when there is an authentication failure
                    override fun onFailure(
                        exception: AuthenticationException
                    ) {
                        loggedIn = false
                    }

                    // Called when authentication completed successfully
                    override fun onSuccess(
                        credentials: Credentials
                    ) {
                        // Get the access token from the credentials object.
                        // This can be used to call APIs
                        val accessToken = credentials.accessToken
                        Toast.makeText(context, accessToken, Toast.LENGTH_SHORT).show()

                        CredentialsManager.saveCredentials(requireContext(), credentials)
                        checkIfToken(viewModel)
                        setLoggedInText()
                    }
                }
            )
    }

    private fun logout() {
        WebAuthProvider.logout(account)
            .withScheme("demo")
            .start(
                requireContext(),
                object : Callback<Void?,
                        AuthenticationException> {
                    override fun onSuccess(payload: Void?) {
                        Toast.makeText(context, "logout OK", Toast.LENGTH_SHORT).show()
                        loggedIn = false
                        setLoggedInText()
                    }

                    override fun onFailure(error: AuthenticationException) {
                        Toast.makeText(context, "logout NOK", Toast.LENGTH_SHORT).show()
                    }
                }
            )
    }

    private fun showUserProfile(accessToken: String, viewModel: LoginViewModel) {
        var client = AuthenticationAPIClient(account)

        // With the access token, call `userInfo` and get the profile from Auth0.
        client.userInfo(accessToken)
            .start(object : Callback<UserProfile, AuthenticationException> {
                override fun onFailure(exception: AuthenticationException) {
                    Timber.i(exception.stackTraceToString())
                    loggedIn = false
                    setLoggedInText()
                }

                override fun onSuccess(profile: UserProfile) {
                    // We have the user's profile!
                    Timber.i("SUCCESS! got the user profile")
                    Timber.i(profile.getId())
                    //getUserMetadata(accessToken)
                    viewModel.addUser(profile)
                    loggedIn = true
                    setLoggedInText()
                }
            })
    }
}
