import { Route, Routes } from 'react-router-dom'
import './App.css'

import Home from './pages/Home.jsx'
import Login from './pages/Login.jsx'
import Signup from './pages/Signup.jsx'
import Diaries from './pages/Diaries.jsx'
import DiaryNew from './pages/DiaryNew.jsx'

function App() {
  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/login" element={<Login />} />
      <Route path="/signup" element={<Signup />} />
      <Route path="/diaries" element={<Diaries />} />
      <Route path="/diaries/new" element={<DiaryNew />} />
    </Routes>
  )
}

export default App