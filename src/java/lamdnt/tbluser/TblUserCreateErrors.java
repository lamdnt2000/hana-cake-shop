/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lamdnt.tbluser;

/**
 *
 * @author sasuk
 */
public class TblUserCreateErrors {
    private String usernameLengthErr;
    private String passwordLengthErr;
    private String confirmErr;
    private String fullnameLengthErr;
    private String userExistedErr;

    public TblUserCreateErrors() {
    }

    public TblUserCreateErrors(String usernameLengthErr, String passwordLengthErr, String confirmErr, 
            String fullnameLengthErr, String userExistedErr) {
        this.usernameLengthErr = usernameLengthErr;
        this.passwordLengthErr = passwordLengthErr;
        this.confirmErr = confirmErr;
        this.fullnameLengthErr = fullnameLengthErr;
        this.userExistedErr = userExistedErr;
    }

    public String getUsernameLengthErr() {
        return usernameLengthErr;
    }

    public void setUsernameLengthErr(String usernameLengthErr) {
        this.usernameLengthErr = usernameLengthErr;
    }

    public String getPasswordLengthErr() {
        return passwordLengthErr;
    }

    public void setPasswordLengthErr(String passwordLengthErr) {
        this.passwordLengthErr = passwordLengthErr;
    }

    public String getConfirmErr() {
        return confirmErr;
    }

    public void setConfirmErr(String confirmErr) {
        this.confirmErr = confirmErr;
    }

    public String getFullnameLengthErr() {
        return fullnameLengthErr;
    }

    public void setFullnameLengthErr(String fullnameLengthErr) {
        this.fullnameLengthErr = fullnameLengthErr;
    }

    public String getUserExistedErr() {
        return userExistedErr;
    }

    public void setUserExistedErr(String userExistedErr) {
        this.userExistedErr = userExistedErr;
    }
    
    
}
