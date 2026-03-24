import client from './client'

export interface LoginRequest {
  email: string
  password: string
}

export interface SignupRequest {
  email: string
  password: string
  nickname: string
}

export const login = (data: LoginRequest) =>
  client.post('/auth/login', data)

export const signup = (data: SignupRequest) =>
  client.post('/auth/signup', data)

export const logout = () =>
  client.post('/auth/logout')
