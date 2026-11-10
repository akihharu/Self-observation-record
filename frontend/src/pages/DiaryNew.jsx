import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'

function getToday() {
  const now = new Date()
  const year = now.getFullYear()
  const month = String(now.getMonth() + 1).padStart(2, '0')
  const day = String(now.getDate()).padStart(2, '0')
  return `${year}-${month}-${day}`
}

function DiaryNew() {
  const [recordDate, setRecordDate] = useState(getToday())
  const [event, setEvent] = useState('')
  const [reflection, setReflection] = useState('')
  const [error, setError] = useState('')
  const [isSubmitting, setIsSubmitting] = useState(false)

  const navigate = useNavigate()

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
      const response = await fetch('http://localhost:8080/api/diaries', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        credentials: 'include',
        body: JSON.stringify({
          recordDate: recordDate,
          event: event,
          reflection: reflection,
          status: status,
        }),
      })

      if (response.status === 401) {
        setError('ログインが必要です。ログインしてください。')
        return
      }

      if (response.status === 400) {
        setError(
          '保存できませんでした。入力内容を確認してください。同じ日付の日記がすでにある可能性があります。'
        )
        return
      }

      if (!response.ok) {
        setError('日記の保存に失敗しました。')
        return
      }

      navigate('/diaries')
    } catch {
      setError('サーバーに接続できませんでした。')
    } finally {
      setIsSubmitting(false)
    }
  }

  return (
    <main className="app-shell">
      <div className="app-container">
        <header className="diary-header">
          <p className="app-eyebrow">SELF OBSERVATION RECORD</p>
          <h1>日記を書く</h1>
          <p>今日の出来事と振り返りを記録しましょう。</p>
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
          <Link to="/diaries">日記一覧へ戻る</Link>
        </footer>
      </div>
    </main>
  )
}

export default DiaryNew