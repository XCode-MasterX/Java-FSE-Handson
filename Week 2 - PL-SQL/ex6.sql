DECLARE
  CURSOR c_transactions IS
    SELECT c.CustomerID, c.Name, t.TransactionDate, t.Amount, t.TransactionType
    FROM Customers c
    JOIN Accounts a ON c.CustomerID = a.CustomerID
    JOIN Transactions t ON a.AccountID = t.AccountID
    WHERE TO_CHAR(t.TransactionDate, 'YYYY-MM') = TO_CHAR(SYSDATE, 'YYYY-MM');

  v_record c_transactions%ROWTYPE;
BEGIN
  DBMS_OUTPUT.PUT_LINE('Monthly Statements:');
  OPEN c_transactions;
  LOOP
    FETCH c_transactions INTO v_record;
    EXIT WHEN c_transactions%NOTFOUND;

    DBMS_OUTPUT.PUT_LINE(
      'Customer: ' || v_record.Name || ' | Date: ' || v_record.TransactionDate ||
      ' | Type: ' || v_record.TransactionType || ' | Amount: ' || v_record.Amount
    );
  END LOOP;
  CLOSE c_transactions;
END;
/

DECLARE
  CURSOR c_accounts IS
    SELECT AccountID, Balance FROM Accounts;

  v_account c_accounts%ROWTYPE;
  v_fee NUMBER := 100; -- flat ₹100 fee
BEGIN
  OPEN c_accounts;
  LOOP
    FETCH c_accounts INTO v_account;
    EXIT WHEN c_accounts%NOTFOUND;

    UPDATE Accounts
    SET Balance = Balance - v_fee
    WHERE AccountID = v_account.AccountID;
  END LOOP;
  CLOSE c_accounts;

  COMMIT;
  DBMS_OUTPUT.PUT_LINE('Annual fee applied to all accounts.');
END;
/

DECLARE
  CURSOR c_loans IS
    SELECT LoanID, InterestRate FROM Loans;

  v_loan c_loans%ROWTYPE;
BEGIN
  OPEN c_loans;
  LOOP
    FETCH c_loans INTO v_loan;
    EXIT WHEN c_loans%NOTFOUND;

    UPDATE Loans
    SET InterestRate = v_loan.InterestRate + 0.5
    WHERE LoanID = v_loan.LoanID;
  END LOOP;
  CLOSE c_loans;

  COMMIT;
  DBMS_OUTPUT.PUT_LINE('Interest rates updated by 0.5%.');
END;
/