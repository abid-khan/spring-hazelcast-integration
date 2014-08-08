package com.hazelcast.config;

import static com.hazelcast.util.Utils.isNull;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.hazelcast.entity.User;

/**
 * @author abidk
 * 
 */
public class AuditorAwareImpl implements AuditorAware<User> {

	@Override
	public User getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (isNull(authentication) || !authentication.isAuthenticated()) {
			return null;
		}

		if (!(authentication.getPrincipal() instanceof UserDetails)) {
			return null;
		}

		return (User) authentication.getPrincipal();
	}

}
