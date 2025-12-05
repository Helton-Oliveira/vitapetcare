export interface LoginRequest {
  email: string,
  password: string
}

export interface TokenRequest {
  refreshToken?: string
}

export interface LoginResponse {
  refreshToken?: string,
  accessToken?: string,
}

export interface TokenResponse {
  accessToken?: string
}

export interface Permissions {
  id?: number
  name?: string,
}
