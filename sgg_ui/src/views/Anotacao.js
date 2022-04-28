import { Component } from "react";
import Table from 'react-bootstrap/Table';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';
import Modal from 'react-bootstrap/Modal';
import OverlayTrigger from 'react-bootstrap/OverlayTrigger';
import Tooltip from 'react-bootstrap/Tooltip';
import { FaTrashAlt, FaRegEye, FaRegEdit } from 'react-icons/fa';
import { AiOutlinePlus } from "react-icons/ai";
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

class Anotacao extends Component {

    constructor (props) {
        super(props);
        this.state = {
            anotacaos: [],
            id: null,
            descricao: '',
            titulo: '',
            dataVencimento: null,
            tipoAnotacao: null,
            status: 'LISTAR',
            usuario: props.usuario,
            modalDelete: false
        };

        this.tipoAnotacaos = [
            {label: 'Diário', value: 'DIARIO' },
            {label: 'Nota', value: 'NOTA' },
            {label: 'Tarefa', value: 'TAREFA' }
        ];
        this.api = 'http://localhost:8080/anotacao';        

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
                this.setState({ anotacaos: res, status: 'LISTAR' });
                this.fecharModais();
            })
            .catch(err => {
                this.setState({ anotacaos: [] })
                console.log(err);
        });
    }    

    buscarTela(id, status) {
        this.callBuscar(id)
            .then(res => this.setState({ 
                id: res.id,
                titulo: res.titulo,
                descricao: res.descricao,
                dataVencimento: res.dataVencimento,
                tipoAnotacao: res.tipoAnotacao,
                usuario: res.usuario,
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
                titulo: '',
                modalDelete: false,
                status: 'LISTAR'
            });
    }     

    criar() {
        this.setState({ 
            descricao: '',
            titulo: '',
            dataVencimento: null,
            tipoAnotacao: null,
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

        const anotacao = {
            descricao: this.state.descricao,
            titulo: this.state.titulo,
            dataVencimento: this.state.dataVencimento,
            tipoAnotacao: this.state.tipoAnotacao,
            usuario: this.state.usuario
        }

        if(this.state.id === null) {
            this.callSalvar(anotacao)
                .then(() => {
                    this.atualizarTela();
                })
                .catch(err => {
                    console.log(err);
            });
        } else {
            this.callAlterar(this.state.id, anotacao)
                .then(() => {
                    this.setState({ id: null })
                    this.atualizarTela();
                })
                .catch(err => {
                    console.log(err);
            });
        }
    }    

    callSalvar = async (anotacao) => {
        
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(anotacao)
        };
        
        const response = await fetch(this.api, requestOptions);
        if (response.status !== 201) throw Error();
    };

    callAlterar = async (id, anotacao) => { 
        const requestOptions = {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(anotacao)
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
  
    abrirModelDelete(id, titulo) {
        
        this.setState(
            {
                id: id,
                titulo: titulo,
                modalDelete: true
            });
    }

    render() {

        return (
            <>
                <Card>
                    <Card.Header as="h5">Anotações</Card.Header>
                    <Card.Body>
                        { (this.state.status === 'LISTAR') && (
                            <>
                                <Button variant="success" onClick={() => this.criar()}><AiOutlinePlus />NOVO</Button>
                                <Table striped bordered hover>
                                    <thead>
                                        <tr>
                                            <th width='35%' >Título</th>
                                            <th width='30%' >Tipo de Anotação</th>
                                            <th width='35%' >Ações</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        {this.state.anotacaos.map((anotacao, index) => {
                                            const { id, titulo, tipoAnotacao } = anotacao;
                                            return (
                                                <tr key={index}>
                                                    <td>{titulo}</td>
                                                    <td>{tipoAnotacao}</td>
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
                                                            overlay={<Tooltip id="button-tooltip-2">Excluir</Tooltip>} >
                                                            <Button variant="danger" onClick={() => this.abrirModelDelete(id, titulo)}> <FaTrashAlt /> </Button>
                                                        </OverlayTrigger>

                                                    </td>
                                                </tr>
                                            )
                                        })}
                                    </tbody>
                                </Table>
                            </>
                        )}
                        { (this.state.status !== 'LISTAR') && (
                            <>
                                <Form>
                                    <Form.Group className="mb-3" controlId="exampleForm.Nome">
                                        <Form.Label>Título</Form.Label>
                                        <Form.Control type="text" name="titulo" onChange={this.handleInputChange} value={this.state.titulo}  disabled={this.state.status==='VISUALIZAR'} />
                                    </Form.Group>
                                    <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
                                        <Form.Label>Descrição</Form.Label>
                                        <Form.Control as="textarea" rows={5} name="descricao" onChange={this.handleInputChange} value={this.state.descricao} readOnly={this.state.status==='VISUALIZAR'}  />
                                    </Form.Group>

                                    {this.state.tipoAnotacao === 'TAREFA' && (
                                        <Row>
                                            <Col>
                                                <Form.Group className="mb-3" controlId="formInstituicao">
                                                    <Form.Label>Tipo de Anotação</Form.Label>
                                                    <Form.Select aria-label="Tipo de anotação" name="tipoAnotacao" value={this.state.tipoAnotacao} onChange={this.handleInputChange} disabled={this.state.status==='VISUALIZAR'} >
                                                        <option value={null}>Selecione o tipo da anotação</option>
                                                        {this.tipoAnotacaos.map((tipoAnotacao, index) => {
                                                            const { label, value } = tipoAnotacao;
                                                            return (
                                                                <option key={index} value={value}>{label}</option>
                                                            )
                                                        })}
                                                    </Form.Select>
                                                </Form.Group>
                                            </Col>
                                            <Col>
                                                <Form.Group className="mb-3" controlId="exampleForm.Nome">
                                                    <Form.Label>Data da Vencimento</Form.Label>
                                                    <Form.Control type="text" name="dataVencimento" onChange={this.handleInputChange} value={this.state.dataVencimento}  disabled={this.state.status==='VISUALIZAR'} />
                                                </Form.Group>
                                            </Col>
                                        </Row>
                                    )}                                    

                                    {this.state.tipoAnotacao !== 'TAREFA' && (
                                        <Form.Group className="mb-3" controlId="formInstituicao">
                                            <Form.Label>Tipo de Anotação</Form.Label>
                                            <Form.Select aria-label="Tipo de anotação" name="tipoAnotacao" value={this.state.tipoAnotacao} onChange={this.handleInputChange} disabled={this.state.status==='VISUALIZAR'} >
                                                <option value={null}>Selecione o tipo da anotação</option>
                                                {this.tipoAnotacaos.map((tipoAnotacao, index) => {
                                                    const { label, value } = tipoAnotacao;
                                                    return (
                                                        <option key={index} value={value}>{label}</option>
                                                    )
                                                })}
                                            </Form.Select>
                                        </Form.Group>
                                    )}
                                    
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
                        <Modal.Title>Exclusão de Anotação</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>Deseja realmente deletar a anotação {this.state.titulo}!</Modal.Body>
                    <Modal.Footer>
                        <Button variant="danger" onClick={() => this.fecharModais()}>
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

export default Anotacao;