import Table from 'react-bootstrap/Table';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';
import Modal from 'react-bootstrap/Modal';

import { Component } from 'react';
import Api from '../services/api';

class Instituicao extends Component {

    constructor (props) {
        super(props);
        this.state = {
            instituicaos: [],
            id: null,
            nome: '',
            endereco: '',
            avaliacao: 0,
            status: 'LISTAR',
            modalDelete: false
        };
        
        this.url = '/instituicaoEnsino';  

        this.handleInputChange = this.handleInputChange.bind(this);
        
    }   

    componentDidMount() {
        this.callListarApi();
    }

    callListarApi = () => {
        Api.get(this.url)
        .then(res => {this.setState({ instituicaos: res.data })})
        .catch(err => { this.setState({ instituicaos: [] }); console.log(err.message); })
    };

    callBuscarApi = async (id, status) => {
        await Api.get(this.url+'/'+id)
        .then(res => {
            this.setState({ 
                id: res.data.id,
                nome: res.data.nome,
                endereco: res.data.endereco,
                avaliacao: res.data.avaliacao,
                status: status
            })
        })
        .catch(err => { console.log(err.message); throw Error(); })
    };

    editar(id) {
        this.buscarTela(id, 'EDITAR');
    }

    buscarTela(id, status) {
        this.callBuscarApi(id, status);
    }

    criar() {
        this.setState({
            id: null,
            nome: '',
            endereco: '',
            avaliacao: 0,
            status: 'CRIAR'
        });
    }

    visualizar(id) {
        this.buscarTela(id, 'VISUALIZAR');
    }

    salvar() {

        const instituicao = {
            nome: this.state.nome,
            endereco: this.state.endereco,
            avaliacao: this.state.avaliacao,
        }

        if(this.state.id === null) {
            this.callCriarApi(instituicao)
                .then(() => {
                    this.setState({ status: 'LISTAR' });
                    this.callListarApi();
                })
                .catch(err => {
                    console.log(err);
                    throw Error();
            });
        } else {
            this.callAlterarApi(this.state.id, instituicao, 'LISTAR')
                .then(() => {
                    this.setState({ status: 'LISTAR' });
                    this.setState({ id: null })
                    this.callListarApi();
                })
                .catch(err => {
                    console.log(err);
                    throw Error();
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

        if(name === 'avaliacao' && value > 10) {
            return;
        }

        if(name !== 'avaliacao' && value.length > 65) {
            return;
        }

        this.setState ({
            [name]: value
        });

    }

    callAlterarApi = async (id, instituicao) => {        
        return await Api.put(this.url+'/'+id, instituicao);
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
                this.callListarApi();
            })
            .catch(err => {
                console.log(err);
                throw Error();
        });
    }

    callDeletarApi = async (id) => {        
        return await Api.delete(this.url + '/' + id);
    };

    callCriarApi = async (instituicao) => {
        return await Api.post(this.url, instituicao);
    };

    render() {
        return (
            <>
                <Card>
                    <Card.Header as="h5">Instituição de Ensino</Card.Header>
                    <Card.Body>
                        { this.state.status === 'LISTAR' && (
                            <>
                                <Button variant="success" onClick={() => this.criar()}>NOVO</Button>
                                <Table striped bordered hover>
                                    <thead>
                                        <tr>
                                            <th>Nome</th>
                                            <th>Endereço</th>
                                            <th>Avaliação</th>
                                            <th>Ações</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        {this.state.instituicaos.map((instituicao, index) => {
                                            const { id, nome, endereco, avaliacao } = instituicao;
                                            return (
                                                <tr key={index}>
                                                    <td>{nome}</td>
                                                    <td>{endereco}</td>
                                                    <td>{avaliacao}</td>
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
                                        <Form.Label>Endereço</Form.Label>
                                        <Form.Control type="text" name="endereco" onChange={this.handleInputChange} value={this.state.endereco} disabled={this.state.status==='VISUALIZAR'}  />
                                    </Form.Group>
                                    <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
                                        <Form.Label>Avaliação</Form.Label>
                                        <Form.Control type="number" name="avaliacao" onChange={this.handleInputChange} value={this.state.avaliacao} disabled={this.state.status==='VISUALIZAR'}  />
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
                    <Modal.Title>Exclusão de Instituição de Ensino</Modal.Title>
                </Modal.Header>
                <Modal.Body>Deseja realmente deletar a instituição {this.state.nome}!</Modal.Body>
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
export default Instituicao;