import {createContext, useContext, useState, useRef, useEffect} from 'react'

export const AuthContext = createContext(null)
export const useAuth = () => useContext(AuthContext)

export const AuthProvider = ({children}) => {
    const [token, setToken] = useState(null)

    return (
        <AuthContext.Provider value={{token, setToken}}>
            {children}
        </AuthContext.Provider>
    )
}
