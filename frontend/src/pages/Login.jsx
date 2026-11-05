import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'

function Login() {
  const [userId, setUserId] = useState('')
  const [password, setPassword] = useState('')
  const [error, setError] = useState('')
  const [isSubmitting, setIsSubmitting] = useState(false)

  const navigate = useNavigate()

  async function handleSubmit(event) {
    event.preventDefault()

    setError('')
    setIsSubmitting(true)

    try {
      const response = await fetch('http://localhost:8080/api/users/login', {
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
        if (response.status === 401) {
          setError('ユーザーIDまたはパスワードが正しくありません')
        } else {
          setError('ログインに失敗しました。入力内容を確認してください')
        }
        return
      }

      navigate('/diaries')
    } catch {
      setError('サーバーに接続できませんでした')
    } finally {
      setIsSubmitting(false)
    }
  }

  return (
    <main className="auth-shell">
      <div className="auth-container">
        <h1>ログイン</h1>

        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="userId">ユーザーID</label>
            <input
              id="userId"
              type="text"
              value={userId}
              onChange={(event) => setUserId(event.target.value)}
              placeholder="ユーザーIDを入力"
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
            />
          </div>

          {error && <p>{error}</p>}

          <button type="submit" disabled={isSubmitting}>
            {isSubmitting ? 'ログイン中...' : 'ログイン'}
          </button>
        </form>

        <p>
          アカウントをお持ちでない方は
          <Link to="/signup">ユーザー登録</Link>
        </p>

        <Link to="/">トップへ戻る</Link>
      </div>
    </main>
  )
}

export default Login