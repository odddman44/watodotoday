import { useEffect, useState } from "react";
import { Navigate} from "react-router-dom";
import { me } from '../api/auth'

export default function ProtectedRoute({ children }: { children: React.ReactNode }) {
    const [status, setStatus] = useState<'loading' | 'ok' | 'unauth'>('loading')

    useEffect(() => {
        me()
            .then(() => setStatus('ok'))
            .catch(() => setStatus('unauth'))
        }, [])

    if (status === 'loading') return null
    if (status === 'unauth') return <Navigate to="/login" replace />
    return <>{children}</>
}