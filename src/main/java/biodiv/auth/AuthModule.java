package biodiv.auth;

import org.pac4j.core.authorization.authorizer.RequireAnyRoleAuthorizer;
import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.core.credentials.UsernamePasswordCredentials;
import org.pac4j.core.credentials.authenticator.Authenticator;
import org.pac4j.http.client.direct.CookieClient;
import org.pac4j.http.client.direct.DirectFormClient;
import org.pac4j.http.client.direct.HeaderClient;
import org.pac4j.http.client.indirect.FormClient;
import org.pac4j.jax.rs.features.JaxRsContextFactoryProvider;
import org.pac4j.jax.rs.features.Pac4JSecurityFeature;
import org.pac4j.jax.rs.jersey.features.Pac4JValueFactoryProvider;
import org.pac4j.jax.rs.servlet.features.ServletJaxRsContextFactoryProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.servlet.ServletModule;

import biodiv.auth.register.RegisterController;
import biodiv.auth.token.Token;
import biodiv.auth.token.TokenDao;
import biodiv.auth.token.TokenService;

public class AuthModule extends ServletModule {
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Override
	protected void configureServlets() {
		log.debug("Configuring AuthModule Servlets");

		bind(Token.class);

		bind(TokenDao.class).in(Singleton.class);
		bind(TokenService.class).in(Singleton.class);

		bind(SimpleUsernamePasswordAuthenticator.class).in(Singleton.class);
		bind(BiodivJaxRsProfileManager.class).in(Singleton.class);

		bind(LoginController.class).in(Singleton.class);
		bind(LogoutController.class).in(Singleton.class);
		bind(RegisterController.class).in(Singleton.class);
		
		//final Config config = new Config();
		//config.setHttpActionAdapter(new Pac4jUserHttpActionAdapter());
		//bind(Config.class).toInstance(config);
		bind(Pac4jConfig.class).asEagerSingleton();
		bind(JaxRsContextFactoryProvider.class).asEagerSingleton();
		bind(Pac4JValueFactoryProvider.Binder.class).asEagerSingleton();
		bind(Pac4JSecurityFeature.class).asEagerSingleton();
		bind(ServletJaxRsContextFactoryProvider.class).asEagerSingleton();
	}

/*	@Provides
	protected FacebookClient provideFacebookClient() {
		final String fbId = "115305755799166";// configuration.getString("fbId");
		final String fbSecret = "efe695fb1a053bdd155e4a4ca153d409";// configuration.getString("fbSecret");
		final FacebookClient facebookClient = new FacebookClient("115305755799166", "efe695fb1a053bdd155e4a4ca153d409");
		//facebookClient.setStateData("biodiv-api-state");
		facebookClient.setAuthenticator(new CustomOAuth20Authenticator(facebookClient.getConfiguration()));
		facebookClient.setCallbackUrl("http://api.local.ibp.org/login/callback?client_name=facebookClient");
		return facebookClient;
	}

	@Provides
	protected Google2Client provideGoole2Client() {
		final String googleId = "317806372709-roromqiujiji1po5jh8adpcr5um895mb.apps.googleusercontent.com";// configuration.getString("fbId");
		final String googleSecret = "x4QjtRV6n2f6cHjH8tl5epVn";// configuration.getString("fbSecret");
		final Google2Client google2Client = new Google2Client(googleId, googleSecret);
		// google2Client.setStateData("biodiv-api-state");
		google2Client.setAuthenticator(new CustomOAuth20Authenticator(google2Client.getConfiguration()));
		google2Client.setProfileCreator(new CustomOAuth2ProfileCreator(google2Client.getConfiguration()));
		google2Client.setCallbackUrl("http://api.local.ibp.org/login/callback?client_name=google2Client");

		return google2Client;
	}

	@Provides
	protected CookieClient provideCookieClient() {
		final CookieClient cookieClient = new CookieClient("BAToken", new CustomJwtAuthenticator(
				new org.pac4j.jwt.config.signature.SecretSignatureConfiguration(Constants.JWT_SALT)));
		return cookieClient;
	}

	@Provides
	protected HeaderClient provideHeaderClient() {
		final HeaderClient headerClient = new HeaderClient("X-AUTH-TOKEN", new CustomJwtAuthenticator(
				new org.pac4j.jwt.config.signature.SecretSignatureConfiguration(Constants.JWT_SALT)));
		return headerClient;
	}

	@Provides
	protected Config provideConfig(FacebookClient facebookClient, Google2Client google2Client,
			HeaderClient headerClient, CookieClient cookieClient) {
		log.debug("Creating pac4j security configuration with clients, authorizers and profilecreators");
		final Clients clients = new Clients(facebookClient, google2Client, headerClient, cookieClient);
		clients.setDefaultClient(cookieClient);

		final Config config = new Config(clients);
		config.addAuthorizer("ROLE_USER", new RequireAnyRoleAuthorizer("ROLE_USER"));
		config.addAuthorizer("ROLE_ADMIN", new RequireAnyRoleAuthorizer("ROLE_ADMIN"));
		// config.addAuthorizer("custom", new CustomAuthorizer());
		return config;
	}
*/
}
