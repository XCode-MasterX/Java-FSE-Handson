-- Scenario 1: Apply 1% discount to loan interest rates for customers above 60 years old
begin
  for cust in (select CustomerID, DOB from Customers) loop
    if MONTHS_BETWEEN(sysdate, cust.DOB) / 12 > 60 then
      update Loans
      set InterestRate = InterestRate - 1
      where CustomerID = cust.CustomerID;
    end if;
  end loop;
end;
/

-- Scenario 2: Promote customers to VIP if balance > $10,000
-- Ensure IsVIP column exists
begin
  execute immediate 'ALTER TABLE Customers ADD (IsVIP VARCHAR2(5))';
EXCEPTION
  when OTHERS then
    if SQLCODE = -01430 then
      NULL; -- column already exists
    else
      RAISE;
    end if;
end;
/

begin
  for cust in (select CustomerID, Balance from Customers) loop
    if cust.Balance > 10000 then
      update Customers
      set IsVIP = 'TRUE'
      where CustomerID = cust.CustomerID;
    end if;
  end loop;
end;
/

-- Scenario 3: Send reminders for loans due in the next 30 days
begin
  for loan_rec in (
    select l.LoanID, c.Name, l.EndDate
    from Loans l
    join Customers c ON l.CustomerID = c.CustomerID
    where l.EndDate <= sysdate + 30
  ) loop
    DBMS_OUTPUT.PUT_LinE('Reminder: Loan ' || loan_rec.LoanID ||
                         ' for customer ' || loan_rec.Name ||
                         ' is due on ' || TO_CHAR(loan_rec.EndDate, 'YYYY-MM-DD'));
  end loop;
end;
/
