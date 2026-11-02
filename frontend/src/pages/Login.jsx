import { useState } from 'react'
import { Link } from 'react-router-dom'

function Login() {
  const [userId, setUserId] = useState('')
  const [password, setPassword] = useState('')

  function handleSubmit(event) {
    event.preventDefault()

    console.log('ユーザーID:', userId)
    console.log('パスワード:', password)
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

          <button type="submit">ログイン</button>
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