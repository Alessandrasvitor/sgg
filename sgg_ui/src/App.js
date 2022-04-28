import Navbar from 'react-bootstrap/Navbar';
import Nav from 'react-bootstrap/Nav';
import Container from 'react-bootstrap/Container';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import { Component } from 'react';
import Routes from './routes';
import Api from './services/api';

class App extends Component {
  constructor (props) {
    super(props);
    this.state = {
      email: '',
      senha: '',
      usuarioLogado: {},
      online: true,
      modalSenha: false
    };
    this.url = '/usuario';
    this.menus = [
      {label: 'Usuários', url: '/usuarios', permissao: '1', componente: 'Usuario'},
      {label: 'Cursos', url: '/cursos', permissao: '5', componente: 'Curso'},
      {label: 'Livros', url: '/livros', permissao: '3', componente: 'Livro'},
      {label: 'Anotações', url: '/anotacoes', permissao: '5', componente: 'Anotacao'},
      {label: 'Instituição de Ensino', url: '/instituicoes', permissao: '5', componente: 'Instituicao'},
    ]
    
    this.handleInputChange = this.handleInputChange.bind(this);
    
  }   

  handleInputChange(event) {
    const target = event.target;
    const value = target.value;
    const name = target.name;

    this.setState ({
        [name]: value
    });

  }
  
  loga() {
    const usuario = {email: this.state.email, senha: this.state.senha};
    
    this.callLogarApi(usuario)
    .then((res) => {
        this.setState({ online: true, usuario: res.data });
    })
    .catch(err => { console.log(err.message); throw Error(); });
  }

  abrirModalSenha() {

  }
  
  logout() {
    this.setState({ online: false, usuario: null });
  }  

  callLogarApi = async (usuario) => {
    return await Api.put(this.url, usuario);
}; 

  render() {
    return (
      <>
        { this.state.online && (
          <>
            <Navbar bg="primary" variant="dark">
              <Container>
                <Navbar.Brand href="/">Home</Navbar.Brand>
                <Nav className="me-auto">
                  {this.menus.map((menu, index) => {
                    const { label, url } = menu;
                    return (
                      <Nav.Link key={index} href={url}>{label}</Nav.Link>
                    );
                  })}
                </Nav>
                <Button variant="light" onClick={() => this.logout()}>Logout</Button>
              </Container>
            </Navbar>
            <Routes />
          </>
        )}
        
        { !this.state.online && (
          <>
            <Form>
              <Form.Group className="mb-3" controlId="formBasicEmail">
                <Form.Label>Email</Form.Label>
                <Form.Control type="email"name="email" onChange={this.handleInputChange} value={this.state.email}  placeholder="Enter email" />
              </Form.Group>
            
              <Form.Group className="mb-3" controlId="formBasicPassword">
                <Form.Label>Senha</Form.Label>
                <Form.Control type="password" name="senha" onChange={this.handleInputChange} value={this.state.senha}  />
              </Form.Group>

              { true === false && (    
                <>
                  <Button variant="link" onClick={() => this.abrirModalSenha()}>Esqueci minha senha</Button>
                  <br />
                </>            
              )}
              
              <Button variant="primary" onClick={() => this.loga()}>LOGAR</Button>
            </Form>
          </>
        )}
      </>
    );
  }
}

export default App;