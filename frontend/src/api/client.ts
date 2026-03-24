import axios from 'axios'

const client = axios.create({
  baseURL: '/api',
  withCredentials: true, // HttpOnly 쿠키 자동 전송
})

// 401 응답 시 로그인 페이지로 리다이렉트
client.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      window.location.href = '/login'
    }
    return Promise.reject(error)
  }
)

export default client
