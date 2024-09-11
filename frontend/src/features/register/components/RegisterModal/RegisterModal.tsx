import React, { useState } from 'react'
import { Modal } from '../../../../components/Modal/Modal'
import { RegistrationStepCounter } from '../RegistrationStepCounter/RegistrationStepCounter';
import { determineModalContent } from '../../../../utils/RegisterModalUtils';

import "./RegisterModal.css"

export const RegisterModal:React.FC = () => {

  const [step, setStep ] = useState<number>(3);

  const stepButtonClicked = () => {
    step === 1  || step === 4 || step >= 6 ? setStep(step) : setStep(step-1);
  }

  return (
      <Modal> 
        <div className="register-modal">
          <RegistrationStepCounter step={step} changeStep={stepButtonClicked}/>
          <div className='register-modal-content'>
            { determineModalContent(step) }
          </div>
        </div>
      </Modal>
  )
}
