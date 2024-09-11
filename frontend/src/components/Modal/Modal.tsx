import React from 'react'

import "./Modal.css";
import "../../assets/globals.css";

interface ModalProps {
    children: React.ReactNode
}

export const Modal:React.FC<ModalProps> = (props:ModalProps) => {
  return (
    <div className="modal-overlay">
        <div className="modal-container bg-color">
            {props.children}
        </div>
    </div>
  )
}
