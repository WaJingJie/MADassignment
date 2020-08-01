package sg.edu.np.mad.madassignment;

public class OverdueLoanData {
    public String email, isbn, name, duedate;
    public Integer days;
    public Double fee;

    public OverdueLoanData(String e, String i, String n, String dd, Integer d, Double f){
        this.email = e;
        this.isbn = i;
        this.name = n;
        this.duedate = dd;
        this.days = d;
        this.fee = f;
    }

    public void setMyLoanEmail(String myLoanEmail) {
        this.email = myLoanEmail;
    }

    public String getMyLoanEmail(){ return this.email; }

    public void setISBN(String ISBN) {
        this.isbn = ISBN;
    }

    public String getISBN(){
        return this.isbn;
    }

    public void setMyLoanName(String loanName) { this.name = loanName; }

    public String getMyLoanName() { return this.name; }

    public void setDueDate(String dueDate) { this.duedate = dueDate; }

    public String getDueDate() {
        return this.duedate;
    }

    public void setDays(Integer days) { this.days = days; }

    public Integer getDays() {
        return this.days;
    }

    public void setFee(Double fee) { this.fee = fee; }

    public Double getFee() {
        return this.fee;
    }


}
