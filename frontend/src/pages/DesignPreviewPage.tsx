/**
 * 디자인 미리보기 페이지 — /preview
 * A안 / B안 / C안을 나란히 비교
 */

// ─────────────────────────────────────────
// A안: 파스텔 + 둥글둥글
// ─────────────────────────────────────────
function SampleA() {
  const todos = [
    { id: 1, text: '물 2L 마시기', done: true },
    { id: 2, text: '운동 30분', done: false },
    { id: 3, text: '독서 20페이지', done: false },
  ]

  return (
    <div
      className="w-72 rounded-3xl p-5 flex flex-col gap-4"
      style={{ background: 'linear-gradient(135deg, #fce4ec, #e8eaf6)' }}
    >
      <div className="text-center">
        <p className="text-xs text-pink-300 font-medium tracking-widest uppercase">Today</p>
        <h2 className="text-xl font-bold text-pink-500 mt-0.5">3월 24일 월요일</h2>
      </div>

      <div className="flex flex-col gap-2">
        {todos.map((todo) => (
          <div
            key={todo.id}
            className="flex items-center gap-3 bg-white/70 rounded-2xl px-4 py-3 shadow-sm"
          >
            <div
              className={`w-5 h-5 rounded-full border-2 flex items-center justify-center flex-shrink-0 ${
                todo.done
                  ? 'bg-pink-400 border-pink-400'
                  : 'border-pink-200'
              }`}
            >
              {todo.done && <span className="text-white text-xs">✓</span>}
            </div>
            <span
              className={`text-sm ${
                todo.done ? 'line-through text-gray-400' : 'text-gray-600'
              }`}
            >
              {todo.text}
            </span>
          </div>
        ))}
      </div>

      <button className="w-full bg-pink-400 hover:bg-pink-500 text-white rounded-2xl py-3 text-sm font-semibold shadow-md transition-colors">
        + 할 일 추가
      </button>

      <div className="flex justify-center gap-6 text-xs text-purple-300 font-medium">
        {['전체', '오늘', '완료'].map((f) => (
          <button key={f} className="hover:text-purple-500 transition-colors">
            {f}
          </button>
        ))}
      </div>
    </div>
  )
}

// ─────────────────────────────────────────
// B안: 일러스트 포인트 (흰 배경 + 귀여운 이모지/아이콘)
// ─────────────────────────────────────────
function SampleB() {
  const todos = [
    { id: 1, text: '물 2L 마시기', done: true, emoji: '💧' },
    { id: 2, text: '운동 30분', done: false, emoji: '🏃' },
    { id: 3, text: '독서 20페이지', done: false, emoji: '📚' },
  ]

  return (
    <div className="w-72 rounded-2xl p-5 flex flex-col gap-4 bg-white border border-orange-100 shadow-lg">
      <div className="flex items-center justify-between">
        <div>
          <p className="text-xs text-orange-300 font-medium">3월 24일 월요일</p>
          <h2 className="text-lg font-bold text-gray-800">오늘 할 일 🌟</h2>
        </div>
        <div className="text-4xl">🐣</div>
      </div>

      <div className="h-px bg-orange-50" />

      <div className="flex flex-col gap-2">
        {todos.map((todo) => (
          <div
            key={todo.id}
            className={`flex items-center gap-3 rounded-xl px-4 py-3 transition-colors ${
              todo.done ? 'bg-orange-50' : 'bg-gray-50 hover:bg-orange-50'
            }`}
          >
            <span className="text-lg">{todo.emoji}</span>
            <span
              className={`text-sm flex-1 ${
                todo.done ? 'line-through text-gray-400' : 'text-gray-700'
              }`}
            >
              {todo.text}
            </span>
            {todo.done && <span className="text-orange-400 text-xs font-bold">Done!</span>}
          </div>
        ))}
      </div>

      <button className="w-full bg-orange-400 hover:bg-orange-500 text-white rounded-xl py-3 text-sm font-semibold transition-colors flex items-center justify-center gap-1">
        <span className="text-base">✏️</span> 추가하기
      </button>

      <div className="flex justify-center gap-2">
        {['전체', '오늘', '완료', '미완료'].map((f, i) => (
          <button
            key={f}
            className={`text-xs px-3 py-1 rounded-full font-medium transition-colors ${
              i === 0
                ? 'bg-orange-400 text-white'
                : 'bg-gray-100 text-gray-500 hover:bg-orange-100'
            }`}
          >
            {f}
          </button>
        ))}
      </div>
    </div>
  )
}

// ─────────────────────────────────────────
// C안: 스티커 노트 감성
// ─────────────────────────────────────────
function SampleC() {
  const todos = [
    { id: 1, text: '물 2L 마시기', done: true, color: 'bg-yellow-200' },
    { id: 2, text: '운동 30분', done: false, color: 'bg-green-200' },
    { id: 3, text: '독서 20페이지', done: false, color: 'bg-pink-200' },
  ]

  return (
    <div className="w-72 p-5 flex flex-col gap-4" style={{ background: '#fffbf0' }}>
      <div
        className="rounded-sm p-4 shadow-md"
        style={{
          background: '#fef08a',
          boxShadow: '3px 3px 8px rgba(0,0,0,0.12)',
          transform: 'rotate(-0.5deg)',
        }}
      >
        <p className="text-xs text-yellow-600 font-medium tracking-wide">📅 3월 24일 월요일</p>
        <h2 className="text-xl font-bold text-yellow-800 mt-1" style={{ fontFamily: 'Georgia, serif' }}>
          오늘 할 일 ✍️
        </h2>
        <p className="text-xs text-yellow-600 mt-1">1개 완료 · 2개 남음</p>
      </div>

      <div className="flex flex-col gap-3">
        {todos.map((todo, i) => (
          <div
            key={todo.id}
            className={`${todo.color} rounded-sm p-3 shadow-sm flex items-start gap-2`}
            style={{
              boxShadow: '2px 2px 6px rgba(0,0,0,0.1)',
              transform: `rotate(${i % 2 === 0 ? '0.4' : '-0.3'}deg)`,
            }}
          >
            <div
              className={`w-4 h-4 rounded-sm border-2 mt-0.5 flex-shrink-0 flex items-center justify-center ${
                todo.done ? 'bg-gray-500 border-gray-500' : 'border-gray-400'
              }`}
            >
              {todo.done && <span className="text-white text-xs leading-none">✓</span>}
            </div>
            <span
              className={`text-sm font-medium ${
                todo.done ? 'line-through text-gray-400' : 'text-gray-700'
              }`}
              style={{ fontFamily: 'Georgia, serif' }}
            >
              {todo.text}
            </span>
          </div>
        ))}
      </div>

      <button
        className="w-full py-3 text-sm font-bold text-yellow-800 rounded-sm shadow-md transition-opacity hover:opacity-80"
        style={{
          background: '#fde68a',
          boxShadow: '2px 2px 6px rgba(0,0,0,0.12)',
          fontFamily: 'Georgia, serif',
        }}
      >
        + 메모 추가
      </button>

      <div className="flex justify-center gap-4 text-xs text-yellow-700 font-medium">
        {['전체', '오늘', '완료'].map((f) => (
          <button key={f} className="underline hover:text-yellow-900 transition-colors">
            {f}
          </button>
        ))}
      </div>
    </div>
  )
}

// ─────────────────────────────────────────
// 메인 프리뷰 페이지
// ─────────────────────────────────────────
export default function DesignPreviewPage() {
  return (
    <div className="min-h-screen bg-gray-100 py-12 px-4">
      <h1 className="text-2xl font-bold text-center text-gray-700 mb-2">디자인 미리보기</h1>
      <p className="text-center text-gray-400 text-sm mb-10">마음에 드는 안을 골라줘!</p>

      <div className="flex flex-wrap justify-center gap-12">
        <div className="flex flex-col items-center gap-4">
          <div className="bg-white rounded-xl px-5 py-2 shadow-sm text-sm font-bold text-pink-500">A안 — 파스텔 + 둥글둥글</div>
          <SampleA />
        </div>

        <div className="flex flex-col items-center gap-4">
          <div className="bg-white rounded-xl px-5 py-2 shadow-sm text-sm font-bold text-orange-500">B안 — 일러스트 포인트</div>
          <SampleB />
        </div>

        <div className="flex flex-col items-center gap-4">
          <div className="bg-white rounded-xl px-5 py-2 shadow-sm text-sm font-bold text-yellow-700">C안 — 스티커 노트 감성</div>
          <SampleC />
        </div>
      </div>
    </div>
  )
}
