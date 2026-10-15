package com.jiltsa.admin.security;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Restricts an endpoint to ADMIN (and DEVELOPER, which is treated as an admin).
 * Authorities are the bare {@link com.jiltsa.admin.user.Role} names, see AppUser#getAuthorities.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAnyAuthority('ADMIN', 'DEVELOPER')")
public @interface AdminOnly {
}
