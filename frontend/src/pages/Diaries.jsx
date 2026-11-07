import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'

function Diaries() {
  const [diaries, setDiaries] = useState([])
  const [error, setError] = useState('')
  const [isLoading, setIsLoading] = useState(true)

  useEffect(() => {
    async function fetchDiaries() {
      try {
        const response = await fetch('http://localhost:8080/api/diaries', {
          method: 'GET',
          credentials: 'include',
        })

        if (response.status === 401) {
          setError('ログインが必要です。ログインしてください。')
          return
        }

        if (!response.ok) {
          setError('日記の取得に失敗しました。')
          return
        }

        const data = await response.json()
        setDiaries(data)
      } catch {
        setError('サーバーに接続できませんでした。')
      } finally {
        setIsLoading(false)
      }
    }

    fetchDiaries()
  }, [])

  return (
    <main className="app-shell">
      <div className="app-container">
        <header className="diary-header">
          <p className="app-eyebrow">SELF OBSERVATION RECORD</p>
          <h1>日記一覧</h1>
          <p>これまでの記録を振り返りましょう。</p>
        </header>

        {isLoading ? (
          <p>日記を読み込んでいます...</p>
        ) : error ? (
          <div className="diary-message">
            <p>{error}</p>
            <Link to="/login">ログイン画面へ</Link>
          </div>
        ) : diaries.length === 0 ? (
          <div className="diary-message">
            <p>まだ日記がありません。</p>
            <p>最初の日記を記録してみましょう。</p>
          </div>
        ) : (
          <div className="diary-list">
            {diaries.map((diary) => (
              <article className="diary-card" key={diary.id}>
                <div className="diary-card-header">
                  <h2>{diary.recordDate}</h2>
                  <span
                    className={
                      diary.status === 1
                        ? 'diary-status completed'
                        : 'diary-status draft'
                    }
                  >
                    {diary.status === 1 ? '完成' : '下書き'}
                  </span>
                </div>

                <div className="diary-content">
                  <h3>今日の出来事</h3>
                  <p>{diary.event}</p>

                  {diary.status === 1 && (
                    <>
                      <h3>振り返り</h3>
                      <p>{diary.reflection}</p>
                    </>
                  )}
                </div>
              </article>
            ))}
          </div>
        )}

        <footer className="diary-footer">
          <Link to="/">トップへ戻る</Link>
        </footer>
      </div>
    </main>
  )
}

export default Diaries