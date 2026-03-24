const tasks = [
  {
    id: 1,
    title: '화분에 물 주기',
    note: '베란다 민트 먼저 챙기기',
    time: '08:30',
    tone: 'bg-rose-100 text-rose-500',
    done: true,
  },
  {
    id: 2,
    title: '점심 도시락 메모',
    note: '딸기 우유도 같이 사기',
    time: '12:10',
    tone: 'bg-amber-100 text-amber-500',
    done: false,
  },
  {
    id: 3,
    title: '저녁 산책 20분',
    note: '벚꽃길로 천천히 돌기',
    time: '18:40',
    tone: 'bg-sky-100 text-sky-500',
    done: false,
  },
]

const chips = ['Today', 'All', 'Done']

export default function PreviewTwoPage() {
  const completedCount = tasks.filter((task) => task.done).length

  return (
    <main className="min-h-screen overflow-hidden bg-[linear-gradient(180deg,#fff7ef_0%,#fffdf8_42%,#fff6fb_100%)] px-4 py-8 text-stone-700">
      <div className="mx-auto flex max-w-6xl flex-col items-center gap-10 lg:flex-row lg:items-center lg:justify-center">
        <section className="max-w-md text-center lg:text-left">
          <span className="inline-flex rounded-full bg-white/80 px-4 py-1 text-sm font-semibold text-rose-400 shadow-[0_10px_30px_rgba(255,181,197,0.25)] ring-1 ring-rose-100">
            /preview2 sample
          </span>

          <h1
            className="mt-5 text-4xl font-black tracking-tight text-stone-800 sm:text-5xl"
            style={{ fontFamily: '"Trebuchet MS", "Arial Rounded MT Bold", sans-serif' }}
          >
            귀엽고 가벼운
            <br />
            오늘의 Tdo
          </h1>

          <p className="mt-4 text-base leading-7 text-stone-500 sm:text-lg">
            말랑한 파스텔 톤과 둥근 카드 위주로 정리해서,
            <br />
            첫인상은 사랑스럽지만 화면 자체는 단순하게 보이도록 잡았어요.
          </p>

          <div className="mt-7 flex flex-wrap justify-center gap-3 lg:justify-start">
            <div className="rounded-3xl bg-white px-4 py-3 shadow-[0_18px_40px_rgba(245,190,208,0.22)] ring-1 ring-rose-100">
              <p className="text-xs font-semibold uppercase tracking-[0.24em] text-rose-300">
                Mood
              </p>
              <p className="mt-1 text-sm font-semibold text-stone-600">Soft diary + toy sticker</p>
            </div>

            <div className="rounded-3xl bg-white px-4 py-3 shadow-[0_18px_40px_rgba(255,212,163,0.22)] ring-1 ring-amber-100">
              <p className="text-xs font-semibold uppercase tracking-[0.24em] text-amber-300">
                Focus
              </p>
              <p className="mt-1 text-sm font-semibold text-stone-600">복잡한 정보는 최소화</p>
            </div>
          </div>
        </section>

        <section className="relative w-full max-w-sm">
          <div className="absolute -left-6 top-8 h-24 w-24 rounded-full bg-rose-200/40 blur-2xl" />
          <div className="absolute -right-3 bottom-12 h-28 w-28 rounded-full bg-amber-200/40 blur-2xl" />

          <div className="relative rounded-[2.5rem] border border-white/70 bg-[#fffdfb] p-4 shadow-[0_24px_80px_rgba(226,178,196,0.28)]">
            <div className="rounded-[2rem] bg-[linear-gradient(180deg,#ffdbe7_0%,#fff7f3_24%,#ffffff_100%)] p-5">
              <div className="flex items-start justify-between">
                <div>
                  <p className="text-sm font-semibold text-rose-400">Tuesday, March 24</p>
                  <h2
                    className="mt-1 text-2xl font-black text-stone-800"
                    style={{ fontFamily: '"Trebuchet MS", "Arial Rounded MT Bold", sans-serif' }}
                  >
                    Tdo berry day
                  </h2>
                </div>
                <div className="rounded-2xl bg-white/80 px-3 py-2 text-2xl shadow-sm">🍓</div>
              </div>

              <div className="mt-5 rounded-[1.75rem] bg-white/80 p-4 shadow-[inset_0_1px_0_rgba(255,255,255,0.8)] ring-1 ring-rose-50">
                <div className="flex items-center justify-between">
                  <div>
                    <p className="text-xs font-semibold uppercase tracking-[0.24em] text-stone-400">
                      Progress
                    </p>
                    <p className="mt-1 text-lg font-bold text-stone-700">
                      {completedCount}/{tasks.length} done
                    </p>
                  </div>
                  <div className="rounded-full bg-emerald-100 px-3 py-1 text-sm font-semibold text-emerald-500">
                    calm pace
                  </div>
                </div>

                <div className="mt-4 h-3 rounded-full bg-rose-100">
                  <div
                    className="h-3 rounded-full bg-[linear-gradient(90deg,#fb7185_0%,#f9a8d4_100%)]"
                    style={{ width: `${(completedCount / tasks.length) * 100}%` }}
                  />
                </div>
              </div>

              <div className="mt-5 flex gap-2">
                {chips.map((chip, index) => (
                  <button
                    key={chip}
                    className={`rounded-full px-4 py-2 text-sm font-semibold transition-transform hover:-translate-y-0.5 ${
                      index === 0
                        ? 'bg-stone-800 text-white shadow-[0_10px_20px_rgba(41,37,36,0.12)]'
                        : 'bg-white text-stone-500 ring-1 ring-stone-100'
                    }`}
                    type="button"
                  >
                    {chip}
                  </button>
                ))}
              </div>

              <div className="mt-5 space-y-3">
                {tasks.map((task) => (
                  <article
                    key={task.id}
                    className="flex items-center gap-3 rounded-[1.6rem] bg-white px-4 py-4 shadow-[0_14px_28px_rgba(245,199,214,0.18)] ring-1 ring-stone-100"
                  >
                    <button
                      type="button"
                      aria-label={`${task.title} 완료 여부`}
                      className={`flex h-11 w-11 flex-shrink-0 items-center justify-center rounded-2xl ${
                        task.done ? 'bg-rose-400 text-white' : task.tone
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

              <button
                type="button"
                className="mt-5 flex w-full items-center justify-center gap-2 rounded-[1.6rem] bg-[linear-gradient(135deg,#fb7185_0%,#f59e0b_100%)] px-4 py-4 text-base font-bold text-white shadow-[0_18px_35px_rgba(251,113,133,0.28)] transition-transform hover:-translate-y-0.5"
              >
                <span className="text-lg">+</span>
                Add a tiny task
              </button>
            </div>

            <div className="mt-4 flex items-center justify-center gap-2 text-xs font-semibold uppercase tracking-[0.24em] text-stone-300">
              <span>cute</span>
              <span className="h-1 w-1 rounded-full bg-stone-300" />
              <span>clear</span>
              <span className="h-1 w-1 rounded-full bg-stone-300" />
              <span>mobile first</span>
            </div>
          </div>
        </section>
      </div>
    </main>
  )
}
