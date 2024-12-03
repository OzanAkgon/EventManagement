package com.example.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(name = "username")
	    private String username;

	    @Column(name = "password")
	    private String password;

	    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	    @JoinTable(
	        name = "user_events",
	        joinColumns = @JoinColumn(name = "user_id"),
	        inverseJoinColumns = @JoinColumn(name = "event_id")
	    )
	    private Set<Event> events = new HashSet<>();
    
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of();
	}
    
    
}
