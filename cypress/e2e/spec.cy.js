describe('Validações tela de login', () => {

  beforeEach(() => {
    cy.visit('http://localhost:9090/login');
  });

  it('Login valido', () => {
    cy.get('body > div > div.right-login > div > form > div:nth-child(1) > input[type=text]')
      .type('fn4586@gmail.com')

      cy.wait(700);
    
    cy.get('body > div > div.right-login > div > form > div:nth-child(2) > input[type=password]')
      .type('35362960')
    
      cy.wait(700);

    cy.get('.btn-login').click()

    cy.visit('http://localhost:9090/principal');
    
  });

  
  it.only('Validar mensagem de erro, ao digitar senha ou email inválidos', () => {
    cy.get('body > div > div.right-login > div > form > div:nth-child(1) > input[type=text]')
      .type('Joao@gmail.com')
    
    cy.wait(700);

    cy.get('body > div > div.right-login > div > form > div:nth-child(2) > input[type=password]')
      .type('123456')
    
      cy.wait(700);
    
    cy.get('.btn-login').click()

    cy.get('.error-message').should("be.visible")
    
  });
});