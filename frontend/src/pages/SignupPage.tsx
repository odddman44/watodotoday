import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { signup } from '../api/auth'

export default function SignupPage() {
  const navigate = useNavigate()
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [nickname, setNickname] = useState('')
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    setError('')
    setLoading(true)
    try {
      await signup({ email, password, nickname })
      navigate('/login')
    } catch (err: any) {
      setError(err.response?.data?.message ?? '회원가입에 실패했습니다. 다시 시도해주세요.')
    } finally {
      setLoading(false)
    }
  }

  return (
    <main className="min-h-screen bg-[linear-gradient(180deg,#fce4ec_0%,#f3e5f5_45%,#e8eaf6_100%)] flex items-center justify-center px-4 py-12">
      {/* 배경 블러 장식 */}
      <div className="fixed top-20 left-1/4 h-40 w-40 rounded-full bg-pink-300/30 blur-3xl pointer-events-none" />
      <div className="fixed bottom-20 right-1/4 h-40 w-40 rounded-full bg-purple-300/30 blur-3xl pointer-events-none" />

      <div className="relative w-full max-w-sm">
        {/* 외부 카드 */}
        <div className="rounded-[2.5rem] border border-white/70 bg-white/60 p-4 shadow-[0_24px_80px_rgba(226,178,196,0.35)] backdrop-blur-sm">
          {/* 내부 카드 */}
          <div className="rounded-[2rem] bg-[linear-gradient(180deg,#fce4ec_0%,#fdf2ff_30%,#ffffff_100%)] p-6">

            {/* 헤더 */}
            <div className="text-center mb-8">
              <div className="inline-block rounded-2xl bg-white/80 px-4 py-2 text-3xl shadow-sm mb-4">
                🌸
              </div>
              <h1
                className="text-2xl font-black text-stone-800"
                style={{ fontFamily: '"Trebuchet MS", "Arial Rounded MT Bold", sans-serif' }}
              >
                처음 오셨군요!
              </h1>
              <p className="mt-1 text-sm text-stone-400">가입하고 오늘 할 일을 정리해봐요</p>
            </div>

            {/* 폼 */}
            <form onSubmit={handleSubmit} className="flex flex-col gap-4">
              <div className="flex flex-col gap-1.5">
                <label className="text-xs font-semibold uppercase tracking-[0.2em] text-stone-400">
                  닉네임
                </label>
                <input
                  type="text"
                  value={nickname}
                  onChange={(e) => setNickname(e.target.value)}
                  placeholder="귀여운 이름으로 불러드릴게요"
                  required
                  className="rounded-2xl bg-white px-4 py-3 text-sm text-stone-700 placeholder-stone-300 ring-1 ring-stone-100 shadow-sm outline-none focus:ring-2 focus:ring-pink-300 transition-shadow"
                />
              </div>

              <div className="flex flex-col gap-1.5">
                <label className="text-xs font-semibold uppercase tracking-[0.2em] text-stone-400">
                  이메일
                </label>
                <input
                  type="email"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  placeholder="hello@example.com"
                  required
                  className="rounded-2xl bg-white px-4 py-3 text-sm text-stone-700 placeholder-stone-300 ring-1 ring-stone-100 shadow-sm outline-none focus:ring-2 focus:ring-pink-300 transition-shadow"
                />
              </div>

              <div className="flex flex-col gap-1.5">
                <label className="text-xs font-semibold uppercase tracking-[0.2em] text-stone-400">
                  비밀번호
                </label>
                <input
                  type="password"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  placeholder="••••••••"
                  required
                  className="rounded-2xl bg-white px-4 py-3 text-sm text-stone-700 placeholder-stone-300 ring-1 ring-stone-100 shadow-sm outline-none focus:ring-2 focus:ring-pink-300 transition-shadow"
                />
              </div>

              {/* 에러 메시지 */}
              {error && (
                <p className="rounded-2xl bg-rose-50 px-4 py-3 text-sm text-rose-400 ring-1 ring-rose-100">
                  {error}
                </p>
              )}

              {/* 가입 버튼 */}
              <button
                type="submit"
                disabled={loading}
                className="mt-2 flex w-full items-center justify-center gap-2 rounded-[1.6rem] bg-[linear-gradient(135deg,#f472b6_0%,#c084fc_100%)] px-4 py-4 text-base font-bold text-white shadow-[0_18px_35px_rgba(244,114,182,0.3)] transition-transform hover:-translate-y-0.5 disabled:opacity-60 disabled:hover:translate-y-0"
              >
                {loading ? '가입 중...' : '시작하기 🌸'}
              </button>
            </form>

            {/* 로그인 링크 */}
            <p className="mt-6 text-center text-sm text-stone-400">
              이미 계정이 있으신가요?{' '}
              <Link
                to="/login"
                className="font-semibold text-pink-400 hover:text-purple-400 transition-colors"
              >
                로그인
              </Link>
            </p>
          </div>
        </div>
      </div>
    </main>
  )
}
