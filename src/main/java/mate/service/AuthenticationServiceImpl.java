package mate.service;

import java.util.Optional;
import mate.exception.AuthenticationException;
import mate.lib.Inject;
import mate.lib.Service;
import mate.model.Driver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final Logger logger = LogManager.getLogger(AuthenticationServiceImpl.class);
    @Inject
    private DriverService driverService;

    @Override
    public Driver login(String login, String password) throws AuthenticationException {
        Optional<Driver> driver = driverService.findByLogin(login);
        if (driver.orElseThrow(() -> {
            logger.info("User didn't log in");
            return new AuthenticationException("Username or password was incorrect"); })
                .getPassword().equals(password)) {
            logger.info("User logged in");
            return driver.get();
        }
        logger.info("User didn't log in");
        throw new AuthenticationException("Username or password was incorrect");
    }
}
