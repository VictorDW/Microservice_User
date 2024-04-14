package com.pragma.user.adapters.driving.adapter;

import com.pragma.user.adapters.driving.dto.request.AuthRequest;
import com.pragma.user.adapters.driving.dto.response.AuthResponse;

public interface IAuthHandler {

	AuthResponse login(AuthRequest request);
}
