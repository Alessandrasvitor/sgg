import { Component } from "react";
import Table from 'react-bootstrap/Table';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';
import Modal from 'react-bootstrap/Modal';
import OverlayTrigger from 'react-bootstrap/OverlayTrigger';
import Tooltip from 'react-bootstrap/Tooltip';
import { FaTrashAlt, FaRegEye, FaRegEdit } from 'react-icons/fa';
import { BsFillFileEarmarkTextFill } from "react-icons/bs";
import { AiOutlinePlus } from "react-icons/ai";

class Livro extends Component {

    constructor (props) {
        super(props);
        this.state = {
            livros: [],
            id: null,
            nome: '',
            subNome: '',
            genero: null,
            status: 'LISTAR',
            usuario: props.usuario,


            instituicaoEnsino: null,
            idCapitulo: null,
            modalDelete: false,
            modalCapitulo: false
        };

        this.generos = [
            {label: 'Ação', value: 'ACAO' },
            {label: 'Comédia', value: 'COMEDIA' },
            {label: 'Romance', value: 'ROMANCE' },
            {label: 'Suspense', value: 'SUSPENSE' }
        ];

        this.instituicoes = [];
        this.api = 'http://localhost:8080/livro';        

        this.handleInputChange = this.handleInputChange.bind(this);
        
    }

    componentDidMount() {
        this.atualizarTela();
    }

    cancelar() {
        this.setState({ status: 'LISTAR' })
    }

    handleInputChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;

        if(name === 'nota' && value > 10) {
            return;
        }

        this.setState ({
            [name]: value
        });

    }

    callListar = async () => {
        const response = await fetch(this.api);
        const body = await response.json();
        if (response.status !== 200) throw Error(body.message);

        return body;
    };    

    callBuscar = async (id) => {
        const response = await fetch(this.api+'/'+id);
        const body = await response.json();
        if (response.status !== 200) throw Error(body.message);

        return body;
    };

    atualizarTela() {
        this.callListar()
            .then(res => {
                this.setState({ livros: res, status: 'LISTAR' });
                this.fecharModais();
            })
            .catch(err => {
                this.setState({ livros: [] })
                console.log(err);
        });
    }    

    buscarTela(id, status) {
        this.callBuscar(id)
            .then(res => this.setState({ 
                id: res.id,
                nome: res.nome,
                subNome: res.subNome,
                usuario: res.usuario,
                genero: res.genero,
                status: status
            }))
            .catch(err => {
                console.log(err);
        });
    }
  
    fecharModais() {        
        this.setState(
            {
                id: null,
                nome: '',
                modalDelete: false,
                modalCapitulo: false,
                status: 'LISTAR'
            });
    }     

    criar() {
        this.setState({ 
            nome: '',
            subNome: '',
            genero: null,
            idUsuario: null,
            status: 'CRIAR'
        });
    }    

    editar(id) {
        this.buscarTela(id, 'EDITAR');
    }

    visualizar(id) {
        this.buscarTela(id, 'VISUALIZAR');
    }    

    excluir() {
        this.callDeletar(this.state.id)
            .then(() => {
                this.atualizarTela();
            })
            .catch(err => {
                this.fecharModais();
                console.log(err);
        });
    }

    salvar() {

        const livro = {
            nome: this.state.nome,
            subNome: this.state.subNome,
            genero: this.state.genero,
            usuario: this.state.usuario
        }

        if(this.state.id === null) {
            this.callSalvar(livro)
                .then(() => {
                    this.atualizarTela();
                })
                .catch(err => {
                    console.log(err);
            });
        } else {
            this.callAlterar(this.state.id, livro)
                .then(() => {
                    this.setState({ id: null })
                    this.atualizarTela();
                })
                .catch(err => {
                    console.log(err);
            });
        }
    }    

    callSalvar = async (livro) => {
        
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(livro)
        };
        
        const response = await fetch(this.api, requestOptions);
        if (response.status !== 201) throw Error();
    };

    callAlterar = async (id, livro) => { 
        const requestOptions = {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(livro)
        };
        
        const response = await fetch(this.api + '/' + id, requestOptions);
        if (response.status !== 200) throw Error();
    };      

    callDeletar = async (id) => {
        
        const requestOptions = {
            method: 'DELETE',
            headers: { 'Content-Type': 'application/json' }
        };
        
        const response = await fetch(this.api + '/' + id, requestOptions);
        if (response.status !== 200) throw Error();
    };   
  
    abrirModelDelete(id, nome) {
        
        this.setState(
            {
                id: id,
                nome: nome,
                modalDelete: true
            });
    }

    addCapitulo(id, nome) {
        this.setState({ modalCapitulo: true });

        console.log(nome + 'do código ' + id)

    }

    render() {

        return (
            <>
                <Card>
                    <Card.Header as="h5">Livros</Card.Header>
                    <Card.Body>
                        { (this.state.status === 'LISTAR' && !this.state.modalCapitulo) && (
                            <>
                                <Button variant="success" onClick={() => this.criar()}><AiOutlinePlus /> NOVO</Button>
                                <Table striped bordered hover>
                                    <thead>
                                        <tr>
                                            <th width='30%' >Nome</th>
                                            <th width='35%' >Sub Nome</th>
                                            <th width='15%' >Genero</th>
                                            <th width='20%' >Ações</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        {this.state.livros.map((livro, index) => {
                                            const { id, nome, subNome, genero } = livro;
                                            return (
                                                <tr key={index}>
                                                    <td>{nome}</td>
                                                    <td>{subNome}</td>
                                                    <td>{genero}</td>
                                                    <td>                                                         
                                                        <OverlayTrigger
                                                                placement="right"
                                                                delay={{ show: 250, hide: 400 }}
                                                                overlay={<Tooltip id="button-tooltip-2">Editar</Tooltip>} >
                                                            <Button variant="primary" onClick={() => this.editar(id)}><FaRegEdit /></Button>
                                                        </OverlayTrigger>
                                                        <OverlayTrigger
                                                            placement="right"
                                                            delay={{ show: 250, hide: 400 }}
                                                            overlay={<Tooltip id="button-tooltip-2">Visualizar</Tooltip>} >
                                                            <Button variant="info" onClick={() => this.visualizar(id)}> <FaRegEye /> </Button>
                                                        </OverlayTrigger>
                                                        <OverlayTrigger
                                                            placement="right"
                                                            delay={{ show: 250, hide: 400 }}
                                                            overlay={<Tooltip id="button-tooltip-2">Adicionar Capítulo</Tooltip>} >
                                                            <Button variant="secondary" onClick={() => this.addCapitulo(id, nome)}> <BsFillFileEarmarkTextFill /></Button>
                                                        </OverlayTrigger>
                                                        
                                                        <OverlayTrigger
                                                            placement="right"
                                                            delay={{ show: 250, hide: 400 }}
                                                            overlay={<Tooltip id="button-tooltip-2">Excluir</Tooltip>} >
                                                            <Button variant="danger" onClick={() => this.abrirModelDelete(id, nome)}> <FaTrashAlt /> </Button>
                                                        </OverlayTrigger>

                                                    </td>
                                                </tr>
                                            )
                                        })}
                                    </tbody>
                                </Table>
                            </>
                        )}
                        { (this.state.status !== 'LISTAR' && !this.state.modalCapitulo) && (
                            <>
                                <Form>
                                    <Form.Group className="mb-3" controlId="exampleForm.Nome">
                                        <Form.Label>Nome</Form.Label>
                                        <Form.Control type="text" name="nome" onChange={this.handleInputChange} value={this.state.nome}  disabled={this.state.status==='VISUALIZAR'} />
                                    </Form.Group>
                                    <Form.Group className="mb-3" controlId="exampleForm.Nome">
                                        <Form.Label>Sub Nome</Form.Label>
                                        <Form.Control type="text" name="subNome" onChange={this.handleInputChange} value={this.state.subNome}  disabled={this.state.status==='VISUALIZAR'} />
                                    </Form.Group>

                                    <Form.Group className="mb-3" controlId="formInstituicao">
                                        <Form.Label>Gênero</Form.Label>
                                        <Form.Select aria-label="Gênero" name="genero" value={this.state.genero} onChange={this.handleInputChange} disabled={this.state.status==='VISUALIZAR'} >
                                            <option value={null}>Selecione um Gênero</option>
                                            {this.generos.map((genero, index) => {
                                                const { label, value } = genero;
                                                return (
                                                    <option key={index} value={value}>{label}</option>
                                                )
                                            })}
                                        </Form.Select>
                                    </Form.Group>
                                    
                                </Form>
                                <br />
                                <div className="text-right" >
                                    { this.state.status !== 'VISUALIZAR' && (   
                                        <Button variant="primary" onClick={() => this.salvar()} >SALVAR</Button>
                                    )}
                                    <Button variant="danger" onClick={() => this.cancelar()}>VOLTAR</Button>
                                </div>
                            </>
                        )}

                    </Card.Body>
                </Card>                              
  
                <Modal show={this.state.modalDelete} onHide={() => this.fecharModais} animation={false}>
                    <Modal.Header closeButton>
                        <Modal.Title>Exclusão de Livro</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>Deseja realmente deletar o livro {this.state.nome}!</Modal.Body>
                    <Modal.Footer>
                        <Button variant="danger" onClick={() => this.fecharModais()}>
                            Cancelar
                        </Button>
                        <Button variant="primary" onClick={() => this.excluir()}>
                            Confirmar
                        </Button>
                    </Modal.Footer>
                </Modal>

                { this.state.modalCapitulo && (
                    <>
                        <Card>
                            <Card.Header as="h5">Novo Capítulo</Card.Header>
                            <Card.Body>
                                <Form.Group className="mb-3" controlId="exampleForm.nota">
                                    <Form.Control as="textarea" row={10} name="nota" onChange={this.handleInputChange} value={this.state.nota} />
                                </Form.Group>
                            </Card.Body>
                            <Card.Footer>                                
                                <Button variant="danger" onClick={() => this.fecharModais()}>
                                    Cancelar
                                </Button>
                                <Button variant="primary" onClick={() => this.finalizarCurso()}>
                                    Confirmar
                                </Button>
                            </Card.Footer>
                        </Card>
                    </>  
                ) }

            </>
            
        )
    };

}

export default Livro;