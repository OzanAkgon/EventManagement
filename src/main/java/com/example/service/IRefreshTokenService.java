package com.example.service;

import com.example.jwt.AuthResponse;
import com.example.jwt.RefreshTokenRequest;

public interface IRefreshTokenService {

	public AuthResponse refreshToken(RefreshTokenRequest request);
}
