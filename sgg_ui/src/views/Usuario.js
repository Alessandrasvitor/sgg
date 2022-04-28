import Table from 'react-bootstrap/Table';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';
import Modal from 'react-bootstrap/Modal';

import { Component } from 'react';

class Usuario extends Component {

    constructor (props) {
        super(props);
        this.state = {
            usuarios: [],
            id: null,
            nome: '',
            email: '',
            senha: null,
            perfil: '',
            api: '',
            status: 'LISTAR',
            modalDelete: false
        };
        this.api = 'http://localhost:8080/usuario';
        this.perfis = [
            {label: 'Administrador', value: 'ADMIN'},
            {label: 'Cadastrado', value: 'COMUM'},
            {label: 'Premiun', value: 'PREMIUM'},
            {label: 'Super Administrador', value: 'SUPER_ADMIN'},
            {label: 'Visitante', value: 'VISITANTE'}
        ]

        this.handleInputChange = this.handleInputChange.bind(this);
        
    }   

    componentDidMount() {
        this.atualizarTela();
    }

    callListarApi = async () => {
        const response = await fetch(this.api);
        const body = await response.json();
        if (response.status !== 200) throw Error(body.message);

        return body;
    };

    callBuscarApi = async (id) => {
        const response = await fetch(this.api+'/'+id);
        const body = await response.json();
        if (response.status !== 200) throw Error(body.message);

        return body;
    };

    atualizarTela() {
        this.callListarApi()
            .then(res => this.setState({ 
                usuarios: res 
                }))
            .catch(err => {
                this.setState({ usuarios: [] })
                console.log(err);
        });
    }

    editar(id) {
        this.buscarTela(id, 'EDITAR');
    }

    buscarTela(id, status) {
        this.callBuscarApi(id)
            .then(res => this.setState({ 
                id: res.id,
                nome: res.nome,
                email: res.email,
                perfil: res.perfil,
                status: status
            }))
            .catch(err => {
                this.setState({ usuarios: [] })
                console.log(err);
        });
    }

    criar() {
        this.setState({ 
            nome: '',
            email: '',
            perfil: null,
            status: 'CRIAR'
        });
    }

    visualizar(id) {
        this.buscarTela(id, 'VISUALIZAR');
    }

    salvar() {

        const usuario = {
            nome: this.state.nome,
            perfil: this.state.perfil,
            email: this.state.email,
            senha: this.state.senha,
        }

        if(this.state.id === null) {
            this.callCriarApi(usuario)
                .then(() => {
                    this.setState({ status: 'LISTAR' });
                    this.atualizarTela();
                })
                .catch(err => {
                    console.log(err);
            });
        } else {
            this.callAlterarApi(this.state.id, usuario)
                .then(() => {
                    this.setState({ status: 'LISTAR' });
                    this.setState({ id: null })
                    this.atualizarTela();
                })
                .catch(err => {
                    console.log(err);
            });
        }
    }

    cancelar() {
        this.setState({ status: 'LISTAR' })
    }

    handleInputChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;

        this.setState ({
            [name]: value
        });

    }

    callAlterarApi = async (id, usuario) => {
        
        const requestOptions = {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(usuario)
        };
        
        const response = await fetch(this.api + '/' + id, requestOptions);
        const body = await response.json();
        if (response.status !== 200) throw Error(body.message);

        return body;
    }; 
  
    handleClose() {        
        this.setState(
            {
                id: null,
                modalDelete: false
            });
    }       
  
    handleShow(id, nome) {
        
        this.setState(
            {
                id: id,
                nome: nome,
                modalDelete: true
            });
    }

    excluir() {
        this.callDeletarApi(this.state.id)
            .then(() => {
                this.handleClose();
                this.atualizarTela();
            })
            .catch(err => {
                this.handleClose();
                console.log(err);
        });
    }

    callDeletarApi = async (id) => {
        
        const requestOptions = {
            method: 'DELETE',
            headers: { 'Content-Type': 'application/json' }
        };
        
        const response = await fetch(this.api + '/' + id, requestOptions);
        if (response.status !== 200) throw Error();
    };

    callCriarApi = async (usuario) => {
        
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(usuario)
        };
        
        const response = await fetch(this.api, requestOptions);
        if (response.status !== 201) throw Error();

        return response;
    };

    render() {
        return (
            <>
                <Card>
                    <Card.Header as="h5">Usuário</Card.Header>
                    <Card.Body>
                        { this.state.status === 'LISTAR' && (
                            <>
                                <Button variant="success" onClick={() => this.criar()}>NOVO</Button>
                                <Table striped bordered hover>
                                    <thead>
                                        <tr>
                                            <th>Nome</th>
                                            <th>Email</th>
                                            <th>Perfil</th>
                                            <th>Ações</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        {this.state.usuarios.map((usuario, index) => {
                                            const { id, nome, perfil, email } = usuario;
                                            return (
                                                <tr key={index}>
                                                    <td>{nome}</td>
                                                    <td>{email}</td>
                                                    <td>{perfil}</td>
                                                    <td> 
                                                        <Button variant="primary" onClick={() => this.editar(id)}>Editar</Button>
                                                        <Button variant="info" onClick={() => this.visualizar(id)}>Visualizar</Button>
                                                        <Button variant="danger" onClick={() => this.handleShow(id, nome)}>Excluir</Button>
                                                    </td>
                                                </tr>
                                            )
                                        })}
                                    </tbody>
                                </Table>
                            </>
                        )}
                        { this.state.status !== 'LISTAR' && (
                            <>
                                <Form>
                                    <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
                                        <Form.Label>Nome</Form.Label>
                                        <Form.Control type="text" name="nome" onChange={this.handleInputChange} value={this.state.nome}  disabled={this.state.status==='VISUALIZAR'} />
                                    </Form.Group>
                                    <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
                                        <Form.Label>Email</Form.Label>
                                        <Form.Control type="email" name="email" onChange={this.handleInputChange} value={this.state.email} disabled={this.state.status==='VISUALIZAR'}  />
                                    </Form.Group>

                                    { this.state.status === 'CRIAR' && (                                
                                        <Form.Group className="mb-3" controlId="formPlaintextPassword">
                                            <Form.Label>Senha</Form.Label>
                                            <Form.Control type="password" name="senha" onChange={this.handleInputChange} value={this.state.senha} disabled={this.state.status==='VISUALIZAR'}  />
                                        </Form.Group>
                                    )}                           
                                    <Form.Group className="mb-3" controlId="formPlaintextPassword">
                                        <Form.Label>Perfil</Form.Label>
                                        <Form.Select aria-label="Perfil" name="perfil" value={this.state.perfil} onChange={this.handleInputChange} disabled={this.state.status==='VISUALIZAR'} >
                                            <option value={null}>Selecione um perfil</option>
                                            {this.perfis.map((perfil) => {
                                                const { label, value } = perfil;
                                                return (
                                                    <option value={value}>{label}</option>
                                                )
                                            })}
                                        </Form.Select>
                                    </Form.Group>
                                    
                                </Form>
                                <br />
                                <div className="text-right" >
                                    { this.state.status !== 'VISUALIZAR' && (   
                                        <Button variant="primary" onClick={() => this.salvar()}>SALVAR</Button>
                                    )}
                                    <Button variant="danger" onClick={() => this.cancelar()}>VOLTAR</Button>
                                </div>
                            </>
                        )}
                    </Card.Body>
                </Card>                
  
                <Modal show={this.state.modalDelete} onHide={() => this.handleClose} animation={false}>
                <Modal.Header closeButton>
                    <Modal.Title>Exclusão de Usuários</Modal.Title>
                </Modal.Header>
                <Modal.Body>Deseja realmente deletar o usuário {this.state.nome}!</Modal.Body>
                <Modal.Footer>
                    <Button variant="danger" onClick={() => this.handleClose()}>
                        Cancelar
                    </Button>
                    <Button variant="primary" onClick={() => this.excluir()}>
                        Confirmar
                    </Button>
                </Modal.Footer>
                </Modal>

            </>
            
        )
    };

}
export default Usuario;