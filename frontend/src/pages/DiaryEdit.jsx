
import { useEffect, useState } from 'react'
import { Link, useNavigate, useParams } from 'react-router-dom'

function DiaryEdit() {
  const { id } = useParams()
  const navigate = useNavigate()

  const [diary, setDiary] = useState(null)
  const [recordDate, setRecordDate] = useState('')
  const [event, setEvent] = useState('')
  const [reflection, setReflection] = useState('')
  const [error, setError] = useState('')
  const [isLoading, setIsLoading] = useState(true)
  const [isSubmitting, setIsSubmitting] = useState(false)

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
          setError('ログインが必要です。ログインしてください。')
          return
        }

        if (response.status === 403) {
          setError('この日記を編集する権限がありません。')
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
        setRecordDate(data.recordDate)
        setEvent(data.event)
        setReflection(data.reflection || '')
      } catch {
        setError('サーバーに接続できませんでした。')
      } finally {
        setIsLoading(false)
      }
    }

    fetchDiary()
  }, [id])

  async function handleSave(status) {
    setError('')

    if (event.trim() === '') {
      setError('今日の出来事を入力してください。')
      return
    }

    if (status === 1 && reflection.trim() === '') {
      setError('完成として保存するには、振り返りも入力してください。')
      return
    }

    setIsSubmitting(true)

    try {
      const response = await fetch(
        `http://localhost:8080/api/diaries/${id}`,
        {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json',
          },
          credentials: 'include',
          body: JSON.stringify({
            recordDate: recordDate,
            event: event,
            reflection: reflection,
            status: status,
            overwrite: false,
          }),
        }
      )

      if (response.status === 401) {
        setError('ログインが必要です。ログインしてください。')
        return
      }

      if (response.status === 403) {
        setError('この日記を編集する権限がありません。')
        return
      }

      if (response.status === 404) {
        setError('日記が見つかりませんでした。')
        return
      }

      if (response.status === 400) {
        setError(
          '保存できませんでした。入力内容を確認してください。同じ日付の日記がすでにある可能性があります。'
        )
        return
      }

      if (!response.ok) {
        setError('日記の更新に失敗しました。')
        return
      }

      navigate(`/diaries/${id}`)
    } catch {
      setError('サーバーに接続できませんでした。')
    } finally {
      setIsSubmitting(false)
    }
  }

  if (isLoading) {
    return <p>日記を読み込んでいます...</p>
  }

  if (error && !diary) {
    return (
      <main className="app-shell">
        <div className="app-container">
          <p>{error}</p>
          <Link to="/diaries">日記一覧へ戻る</Link>
        </div>
      </main>
    )
  }

  if (!diary) {
    return null
  }

  return (
    <main className="app-shell">
      <div className="app-container">
        <header className="diary-header">
          <p className="app-eyebrow">SELF OBSERVATION RECORD</p>
          <h1>日記編集</h1>
          <p>記録した内容を編集しましょう。</p>
        </header>

        <form>
          <div className="form-group">
            <label htmlFor="recordDate">記録日</label>
            <input
              id="recordDate"
              type="date"
              value={recordDate}
              onChange={(e) => setRecordDate(e.target.value)}
            />
          </div>

          <div className="form-group">
            <label htmlFor="event">今日の出来事(300文字以内)</label>
            <textarea
              id="event"
              value={event}
              onChange={(e) => setEvent(e.target.value)}
              maxLength={300}
              rows={5}
              placeholder="今日行ったことを書きましょう"
            />
          </div>

          <div className="form-group">
            <label htmlFor="reflection">振り返り・学び(300文字以内)</label>
            <textarea
              id="reflection"
              value={reflection}
              onChange={(e) => setReflection(e.target.value)}
              maxLength={300}
              rows={5}
              placeholder="気づいたこと、学んだことを書きましょう"
            />
          </div>

          {error && <p>{error}</p>}

          <button
            type="button"
            disabled={isSubmitting}
            onClick={() => handleSave(0)}
          >
            {isSubmitting ? '保存中...' : '下書き保存'}
          </button>

          <button
            type="button"
            disabled={isSubmitting}
            onClick={() => handleSave(1)}
          >
            {isSubmitting ? '保存中...' : '完成保存'}
          </button>
        </form>

        <footer className="diary-footer">
          <Link to={`/diaries/${id}`}>詳細画面へ戻る</Link>
        </footer>
      </div>
    </main>
  )
}

export default DiaryEdit
