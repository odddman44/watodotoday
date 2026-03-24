const tasks = [
  {
    id: 1,
    title: '화분에 물 주기',
    note: '베란다 민트 먼저 챙기기',
    time: '08:30',
    tone: 'bg-pink-100 text-pink-400',
    done: true,
  },
  {
    id: 2,
    title: '점심 도시락 메모',
    note: '딸기 우유도 같이 사기',
    time: '12:10',
    tone: 'bg-purple-100 text-purple-400',
    done: false,
  },
  {
    id: 3,
    title: '저녁 산책 20분',
    note: '벚꽃길로 천천히 돌기',
    time: '18:40',
    tone: 'bg-sky-100 text-sky-400',
    done: false,
  },
]

const filters = ['오늘', '전체', '완료']

export default function PreviewThreePage() {
  const completedCount = tasks.filter((t) => t.done).length

  return (
    <main className="min-h-screen overflow-hidden bg-[linear-gradient(180deg,#fce4ec_0%,#f3e5f5_45%,#e8eaf6_100%)] px-4 py-8 text-stone-700">
      <div className="mx-auto flex max-w-6xl flex-col items-center gap-10 lg:flex-row lg:items-center lg:justify-center">

        {/* 왼쪽 소개 영역 */}
        <section className="max-w-md text-center lg:text-left">
          <h1
            className="mt-5 text-4xl font-black tracking-tight text-pink-500 sm:text-5xl"
            style={{ fontFamily: '"Trebuchet MS", "Arial Rounded MT Bold", sans-serif' }}
          >
            귀엽고 가벼운
            <br />
            오늘의 할 일
          </h1>

          <p className="mt-4 text-base leading-7 text-stone-500 sm:text-lg">
            말랑한 파스텔 톤과 둥근 카드로 정리해서,
            <br />
            첫인상은 사랑스럽지만 화면은 단순하게.
          </p>

          <div className="mt-7 flex flex-wrap justify-center gap-3 lg:justify-start">
            <div className="rounded-3xl bg-white/80 px-4 py-3 shadow-[0_18px_40px_rgba(245,190,208,0.25)] ring-1 ring-pink-100">
              <p className="text-xs font-semibold uppercase tracking-[0.24em] text-pink-300">
                무드
              </p>
              <p className="mt-1 text-sm font-semibold text-stone-600">소프트 다이어리 감성</p>
            </div>

            <div className="rounded-3xl bg-white/80 px-4 py-3 shadow-[0_18px_40px_rgba(200,180,255,0.22)] ring-1 ring-purple-100">
              <p className="text-xs font-semibold uppercase tracking-[0.24em] text-purple-300">
                목표
              </p>
              <p className="mt-1 text-sm font-semibold text-stone-600">복잡한 정보는 최소화</p>
            </div>
          </div>
        </section>

        {/* 오른쪽 카드 영역 */}
        <section className="relative w-full max-w-sm">
          {/* 배경 블러 장식 */}
          <div className="absolute -left-6 top-8 h-24 w-24 rounded-full bg-pink-300/40 blur-2xl" />
          <div className="absolute -right-3 bottom-12 h-28 w-28 rounded-full bg-purple-300/40 blur-2xl" />

          <div className="relative rounded-[2.5rem] border border-white/70 bg-white/60 p-4 shadow-[0_24px_80px_rgba(226,178,196,0.35)] backdrop-blur-sm">
            <div className="rounded-[2rem] bg-[linear-gradient(180deg,#fce4ec_0%,#fdf2ff_30%,#ffffff_100%)] p-5">

              {/* 날짜 + 이모지 */}
              <div className="flex items-start justify-between">
                <div>
                  <p className="text-sm font-semibold text-pink-400">3월 24일 월요일</p>
                  <h2
                    className="mt-1 text-2xl font-black text-stone-800"
                    style={{ fontFamily: '"Trebuchet MS", "Arial Rounded MT Bold", sans-serif' }}
                  >
                    오늘도 귀엽게 🌸
                  </h2>
                </div>
                <div className="rounded-2xl bg-white/80 px-3 py-2 text-2xl shadow-sm">🍓</div>
              </div>

              {/* 진행도 */}
              <div className="mt-5 rounded-[1.75rem] bg-white/80 p-4 shadow-[inset_0_1px_0_rgba(255,255,255,0.8)] ring-1 ring-pink-50">
                <div className="flex items-center justify-between">
                  <div>
                    <p className="text-xs font-semibold uppercase tracking-[0.24em] text-stone-400">
                      진행도
                    </p>
                    <p className="mt-1 text-lg font-bold text-stone-700">
                      {completedCount}/{tasks.length} 완료
                    </p>
                  </div>
                  <div className="rounded-full bg-purple-100 px-3 py-1 text-sm font-semibold text-purple-400">
                    여유롭게 ✨
                  </div>
                </div>

                <div className="mt-4 h-3 rounded-full bg-pink-100">
                  <div
                    className="h-3 rounded-full bg-[linear-gradient(90deg,#f472b6_0%,#c084fc_100%)]"
                    style={{ width: `${(completedCount / tasks.length) * 100}%` }}
                  />
                </div>
              </div>

              {/* 필터 칩 */}
              <div className="mt-5 flex gap-2">
                {filters.map((label, index) => (
                  <button
                    key={label}
                    className={`rounded-full px-4 py-2 text-sm font-semibold transition-transform hover:-translate-y-0.5 ${
                      index === 0
                        ? 'bg-pink-400 text-white shadow-[0_10px_20px_rgba(244,114,182,0.3)]'
                        : 'bg-white text-stone-500 ring-1 ring-stone-100'
                    }`}
                    type="button"
                  >
                    {label}
                  </button>
                ))}
              </div>

              {/* 할 일 카드 목록 */}
              <div className="mt-5 space-y-3">
                {tasks.map((task) => (
                  <article
                    key={task.id}
                    className="flex items-center gap-3 rounded-[1.6rem] bg-white px-4 py-4 shadow-[0_14px_28px_rgba(245,199,214,0.2)] ring-1 ring-stone-100"
                  >
                    <button
                      type="button"
                      aria-label={`${task.title} 완료 여부`}
                      className={`flex h-11 w-11 flex-shrink-0 items-center justify-center rounded-2xl text-base ${
                        task.done ? 'bg-pink-400 text-white' : task.tone
                      }`}
                    >
                      {task.done ? '✓' : '•'}
                    </button>

                    <div className="min-w-0 flex-1">
                      <div className="flex items-center justify-between gap-3">
                        <h3
                          className={`truncate text-sm font-bold ${
                            task.done ? 'text-stone-400 line-through' : 'text-stone-700'
                          }`}
                        >
                          {task.title}
                        </h3>
                        <span className="text-xs font-semibold text-stone-300">{task.time}</span>
                      </div>
                      <p className="mt-1 truncate text-sm text-stone-400">{task.note}</p>
                    </div>
                  </article>
                ))}
              </div>

              {/* 추가 버튼 */}
              <button
                type="button"
                className="mt-5 flex w-full items-center justify-center gap-2 rounded-[1.6rem] bg-[linear-gradient(135deg,#f472b6_0%,#c084fc_100%)] px-4 py-4 text-base font-bold text-white shadow-[0_18px_35px_rgba(244,114,182,0.3)] transition-transform hover:-translate-y-0.5"
              >
                <span className="text-lg">+</span>
                할 일 추가하기
              </button>
            </div>

          </div>
        </section>

      </div>
    </main>
  )
}
