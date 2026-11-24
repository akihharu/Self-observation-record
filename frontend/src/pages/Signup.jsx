import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'

function Signup() {
  const [userId, setUserId] = useState('')
  const [password, setPassword] = useState('')
  const [error, setError] = useState('')
  const [isSubmitting, setIsSubmitting] = useState(false)

  const navigate = useNavigate()

  async function handleSubmit(event) {
    event.preventDefault()

    setError('')

    if (!userId.trim() || !password.trim()) {
      setError('ユーザーIDとパスワードを入力してください')
      return
    }

    if (userId.length < 4 || userId.length > 20) {
      setError('ユーザーIDは4文字以上20文字以下で入力してください')
      return
    }

    if (password.length < 4 || password.length > 20) {
      setError('パスワードは4文字以上20文字以下で入力してください')
      return
    }

    setIsSubmitting(true)

    try {
      const response = await fetch('http://localhost:8080/api/users/signup', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        credentials: 'include',
        body: JSON.stringify({
          userId: userId,
          password: password,
        }),
      })

      if (!response.ok) {
        if (response.status === 400) {
          setError('ユーザーIDがすでに使用されているか、入力内容に問題があります')
        } else {
          setError('ユーザー登録に失敗しました')
        }
        return
      }

      navigate('/login')
    } catch {
      setError('サーバーに接続できませんでした')
    } finally {
      setIsSubmitting(false)
    }
  }

  return (
    <main className="auth-shell">
      <div className="auth-container">
        <h1>ユーザー登録</h1>

        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="userId">ユーザーID</label>
            <input
              id="userId"
              type="text"
              value={userId}
              onChange={(event) => setUserId(event.target.value)}
              placeholder="ユーザーIDを入力"
              autoComplete="username"
            />
          </div>

          <div className="form-group">
            <label htmlFor="password">パスワード</label>
            <input
              id="password"
              type="password"
              value={password}
              onChange={(event) => setPassword(event.target.value)}
              placeholder="パスワードを入力"
              autoComplete="new-password"
            />
          </div>

          {error && <p>{error}</p>}

          <button type="submit" disabled={isSubmitting}>
            {isSubmitting ? '登録中...' : 'ユーザー登録'}
          </button>
        </form>

        <p>
          すでにアカウントをお持ちの方は
          <Link to="/login">ログイン</Link>
        </p>

        <Link to="/">トップへ戻る</Link>
      </div>
    </main>
  )
}

export default Signup