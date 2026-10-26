import './App.css'

function App() {
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

        <footer className="app-footer">
          Self-observation record
        </footer>
      </div>
    </main>
  )
}

export default App