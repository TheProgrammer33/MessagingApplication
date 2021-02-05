package sample;

//
//  Created by: Tutorials Point | Raja | 07-02-2019
//  Link: https://www.tutorialspoint.com/how-to-create-a-custom-unchecked-exception-in-java
//

public class HTTPException extends RuntimeException
{
    public HTTPException(String message)
    {
        super(message);
    }

    public HTTPException(Throwable cause)
    {
        super(cause);
    }

    public HTTPException(String message, Throwable throwable)
    {
        super(message, throwable);
    }
}
