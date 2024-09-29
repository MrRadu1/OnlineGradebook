import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

export default function ToastMessenger(message,type) {

        if(type === "error"){
            toast.error(message);
        }else if(type === "info"){
            toast.info(message);
        }else{
            toast.success(message);
        }
}