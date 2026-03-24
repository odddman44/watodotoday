import { Routes, Route, Navigate } from 'react-router-dom'
import LoginPage from './pages/LoginPage'
import SignupPage from './pages/SignupPage'
import TodoPage from './pages/TodoPage'
import DesignPreviewPage from './pages/DesignPreviewPage'
import PreviewTwoPage from './pages/PreviewTwoPage'
import PreviewThreePage from './pages/PreviewThreePage'

function App() {
  return (
    <Routes>
      <Route path="/login" element={<LoginPage />} />
      <Route path="/signup" element={<SignupPage />} />
      <Route path="/" element={<TodoPage />} />
      <Route path="/preview" element={<DesignPreviewPage />} />
      <Route path="/preview2" element={<PreviewTwoPage />} />
      <Route path="/preview3" element={<PreviewThreePage />} />
      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
  )
}

export default App
