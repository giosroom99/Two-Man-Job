from cryptography.fernet import Fernet

# The  Key will be incrypted and broken in half
message = "EXPLOSA THE WORLD"
operator_1 =""
operator_2 =""
fernet =""

"""
    Fernet guarantees that a message encrypted using it cannot be manipulated or read without the key. 
    Fernet is an implementation of symmetric (also known as “secret key”) authenticated cryptography. 
    Fernet also has support for implementing key rotation via MultiFernet .
    
    <https://cryptography.io/en/latest/fernet/>
"""

key = Fernet.generate_key()
# Instance the Fernet class with the key
fernet = Fernet(key)

def encrypt(): 

    # then use the Fernet class instance
    # to encrypt the string string
    encMessage = fernet.encrypt(message.encode())
    encMessage =encMessage.decode("utf-8")
    # Break the message in halves for two operators ()
    operator_1, operator_2 = encMessage[:int(len(encMessage)/2)], encMessage[int(len(encMessage)/2):]
    
    return(operator_1, operator_2)



def decrypt(operator_1, operator_2):

    combined_key =operator_1 + operator_2
  
    decMessage = fernet.decrypt((combined_key).encode("utf-8"))
    #print("decrypted string: ", decMessage)

print(encrypt()[0])
decrypt(encrypt()[0], encrypt()[1])
