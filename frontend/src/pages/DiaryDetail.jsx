import { useEffect, useState } from 'react'
import { Link, useParams, useNavigate } from 'react-router-dom'

function DiaryDetail() {
  const { id } = useParams()
  const navigate = useNavigate()

  const [diary, setDiary] = useState(null)
  const [error, setError] = useState('')
  const [isLoading, setIsLoading] = useState(true)
  const [requiresLogin, setRequiresLogin] = useState(false)
  const [deleteError, setDeleteError] = useState('')
  const [isDeleting, setIsDeleting] = useState(false)

  useEffect(() => {
    async function fetchDiary() {
      try {
        const response = await fetch(
          `http://localhost:8080/api/diaries/${id}`,
          {
            method: 'GET',
            credentials: 'include',
          }
        )

        if (response.status === 401) {
          setRequiresLogin(true)
          setError('ログインが必要です。ログインしてください。')
          return
        }

        if (response.status === 403) {
          setError('この日記を閲覧する権限がありません。')
          return
        }

        if (response.status === 404) {
          setError('日記が見つかりませんでした。')
          return
        }

        if (!response.ok) {
          setError('日記の取得に失敗しました。')
          return
        }

        const data = await response.json()
        setDiary(data)
      } catch {
        setError('サーバーに接続できませんでした。')
      } finally {
        setIsLoading(false)
      }
    }

    fetchDiary()
  }, [id])

  async function handleDelete(diary) {
    const confirmed = window.confirm(
      `${diary.recordDate}の日記を削除しますか？この操作は元に戻せません。`
    )

    if (!confirmed) {
      return
    }

    setDeleteError('')
    setIsDeleting(true)

    try {
      const response = await fetch(
        `http://localhost:8080/api/diaries/${diary.id}`,
        {
          method: 'DELETE',
          credentials: 'include',
        }
      )

      if (response.status === 401) {
        setRequiresLogin(true)
        setError('ログインが必要です。ログインしてください。')
        return
      }

      if (response.status === 403) {
        setDeleteError('この日記を削除する権限がありません。')
        return
      }

      if (response.status === 404) {
        setDeleteError('削除対象の日記が見つかりませんでした。')
        return
      }

      if (!response.ok) {
        setDeleteError('日記の削除に失敗しました。')
        return
      }

      navigate('/diaries')
    } catch {
      setDeleteError('サーバーに接続できませんでした。')
    } finally {
      setIsDeleting(false)
    }
  }

  return (
    <main className="app-shell">
      <div className="app-container">
        <header className="diary-header">
          <p className="app-eyebrow">SELF OBSERVATION RECORD</p>
          <h1>日記詳細</h1>
          <p>記録した内容を振り返りましょう。</p>
        </header>

        {isLoading ? (
          <p>日記を読み込んでいます...</p>
        ) : error ? (
          <div className="diary-message">
            <p>{error}</p>
              {requiresLogin ? (
                <Link to="/login">ログイン画面へ</Link>
              ) : (
                <Link to="/diaries">日記一覧へ戻る</Link>
              )}
          </div>
        ) : diary ? (
          <article className="diary-detail-card">
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

              <h3>振り返り・学び</h3>
              <p>
                {diary.reflection || 'まだ振り返りは記録されていません。'}
              </p>
            </div>

            {deleteError && (
              <p className="diary-delete-error" role="alert">
                {deleteError}
              </p>
            )}

            <div className="diary-card-actions">
              <button
                type="button"
                onClick={() => handleDelete(diary)}
                disabled={isDeleting}
              >
                {isDeleting ? '削除中...' : '削除'}
              </button>
            </div>
          </article>
        ) : null}

        <footer className="diary-footer">
          <Link to="/diaries">日記一覧へ戻る</Link>
        </footer>
      </div>
    </main>
  )
}

export default DiaryDetail