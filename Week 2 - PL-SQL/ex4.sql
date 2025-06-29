CREATE OR REPLACE FUNCTION CalculateAge (
  p_dob IN DATE
) RETURN NUMBER IS
  v_age NUMBER;
BEGIN
  v_age := FLOOR(MONTHS_BETWEEN(SYSDATE, p_dob) / 12);
  RETURN v_age;
END;
/

CREATE OR REPLACE FUNCTION CalculateMonthlyInstallment (
  p_loan_amount IN NUMBER,
  p_annual_rate IN NUMBER,
  p_years       IN NUMBER
) RETURN NUMBER IS
  v_monthly_rate NUMBER;
  v_months       NUMBER;
  v_emi          NUMBER;
BEGIN
  v_monthly_rate := p_annual_rate / 12 / 100;
  v_months := p_years * 12;

  v_emi := p_loan_amount * v_monthly_rate * POWER(1 + v_monthly_rate, v_months)
           / (POWER(1 + v_monthly_rate, v_months) - 1);

  RETURN ROUND(v_emi, 2);
END;
/

CREATE OR REPLACE FUNCTION HasSufficientBalance (
  p_account_id IN NUMBER,
  p_amount     IN NUMBER
) RETURN BOOLEAN IS
  v_balance NUMBER;
BEGIN
  SELECT Balance INTO v_balance
  FROM Accounts
  WHERE AccountID = p_account_id;

  RETURN v_balance >= p_amount;
EXCEPTION
  WHEN NO_DATA_FOUND THEN
    RETURN FALSE;
END;
/
