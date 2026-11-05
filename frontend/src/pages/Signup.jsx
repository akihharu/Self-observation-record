import { Link } from 'react-router-dom'

function Signup() {
  return (
    <main className="app-shell">
      <div className="app-container">
        <h1>ユーザー登録画面（仮）</h1>
        <p>画面遷移の確認用です。</p>
        <Link to="/">トップへ戻る</Link>
      </div>
    </main>
  )
}

export default Signup