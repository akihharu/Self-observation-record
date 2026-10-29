import { Link, Route, Routes } from 'react-router-dom'
import './App.css'

// トップ画面
function Home() {
  return (
    <main className="app-shell">
      <div className="app-container">
        <header className="app-header">
          <p className="app-eyebrow">SELF-OBSERVATION RECORD</p>

          <h1>日々の記録を、次の一歩へ。</h1>

          <p className="app-description">
            その日の出来事や感じたことを記録し、
            日々の振り返りや学びにつなげるためのアプリです。
          </p>
        </header>

        <section className="setup-panel">
          <p className="panel-label">DEVELOPMENT STATUS</p>

          <h2>フロントエンドの準備ができました</h2>

          <p>
            Reactによる画面の土台を作成しました。
            今後、Spring BootのAPIと連携して、
            日記の登録や閲覧などの機能を実装していきます。
          </p>

          <div className="tech-list">
            <span className="tech-tag">React</span>
            <span className="tech-tag">Vite</span>
            <span className="tech-tag">Spring Boot</span>
          </div>
        </section>

        <nav>
          <p><Link to="/login">ログイン画面へ</Link></p>
          <p><Link to="/signup">ユーザー登録画面へ</Link></p>
          <p><Link to="/diaries">日記一覧画面へ</Link></p>
        </nav>

        <footer className="app-footer">
          Self-observation record
        </footer>
      </div>
    </main>
  )
}

// ログイン画面（仮）
function Login() {
  return (
    <main className="app-shell">
      <div className="app-container">
        <h1>ログイン画面（仮）</h1>
        <p>画面遷移の確認用です。</p>
        <Link to="/">トップへ戻る</Link>
      </div>
    </main>
  )
}

// ユーザー登録画面（仮）
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

// 日記一覧画面（仮）
function Diaries() {
  return (
    <main className="app-shell">
      <div className="app-container">
        <h1>日記一覧画面（仮）</h1>
        <p>画面遷移の確認用です。</p>
        <Link to="/">トップへ戻る</Link>
      </div>
    </main>
  )
}

function App() {
  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/login" element={<Login />} />
      <Route path="/signup" element={<Signup />} />
      <Route path="/diaries" element={<Diaries />} />
    </Routes>
  )
}

export default App
