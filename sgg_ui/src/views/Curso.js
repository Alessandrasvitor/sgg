import Table from 'react-bootstrap/Table';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';
import Modal from 'react-bootstrap/Modal';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Pagination from 'react-bootstrap/Pagination';
import OverlayTrigger from 'react-bootstrap/OverlayTrigger';
import Tooltip from 'react-bootstrap/Tooltip';
import { FaTrashAlt, FaRegEye, FaRegEdit, FaUndoAlt, FaArrowRight, FaCheck } from 'react-icons/fa';
import { AiOutlinePlus } from "react-icons/ai";

import { Component } from 'react';

class Curso extends Component {

    constructor (props) {
        super(props);
        this.state = {
            cursos: [],
            id: null,
            nome: '',
            descricao: '',
            dataInicioFormatada: '',
            dataFimFormatada: '',
            status: null,
            usuario: props.usuario,
            instituicaoEnsino: null,
            idInstituicao: null,
            nota: 0,
            estado: 'LISTAR',
            modalDelete: false,
            modalFinalizar: false
        };

        this.statusList = [
            {label: 'Pendente', value: 'PENDENTE' },
            {label: 'Iniciado', value: 'INICIADO' },
            {label: 'Concluído', value: 'CONCLUIDO' },
            {label: 'Reprovado', value: 'REPROVADO' }
        ];

        this.instituicoes = [];
        this.atualizarInstituicoes();
        this.api = 'http://localhost:8080/curso';        

        this.handleInputChange = this.handleInputChange.bind(this);
        
    }      

    atualizarInstituicoes() {
        this.callListarInstituicoesApi()
            .then(res => this.instituicoes = res)
            .catch(err => {
                this.setState({ cursos: [] })
                console.log(err);
        });
    } 
    

    callListarInstituicoesApi = async () => {
        const response = await fetch('http://localhost:8080/instituicaoEnsino');
        const body = await response.json();
        if (response.status !== 200) throw Error(body.message);

        return body;
    };

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
            .then(res => {
                this.setState({ cursos: res });
                this.fecharModais();
            })
            .catch(err => {
                this.setState({ cursos: [] })
                console.log(err);
        });
    }

    editar(id) {
        this.buscarTela(id, 'EDITAR');
    }

    buscarTela(id, estado) {
        this.callBuscarApi(id)
            .then(res => this.setState({ 
                id: res.id,
                nome: res.nome,
                descricao: res.descricao,
                dataInicioFormatada: res.dataInicioFormatada,
                dataFimFormatada: res.dataFimFormatada,
                status: res.status,
                usuario: res.usuario,
                nota: res.nota,
                instituicaoEnsino: res.instituicaoEnsino,
                idInstituicao: res.idInstituicao,
                estado: estado
            }))
            .catch(err => {
                console.log(err);
        });
    }

    criar() {
        this.setState({ 
            nome: '',
            descricao: '',
            status: null,
            nota: 0,
            idInstituicao: null,
            estado: 'CRIAR'
        });
    }

    visualizar(id) {
        this.buscarTela(id, 'VISUALIZAR');
    }

    salvar() {

        const curso = {
            nome: this.state.nome,
            descricao: this.state.descricao,
            status: this.state.status,
            usuario: this.state.usuario,
            instituicaoEnsino: this.state.instituicaoEnsino,
            idInstituicao: this.state.idInstituicao,
            nota: this.state.nota,
        }

        if(this.state.id === null) {
            this.callCriarApi(curso)
                .then(() => {
                    this.setState({ estado: 'LISTAR' });
                    this.atualizarTela();
                })
                .catch(err => {
                    console.log(err);
            });
        } else {
            this.callAlterarApi(this.state.id, curso)
                .then(() => {
                    this.setState({ estado: 'LISTAR' });
                    this.setState({ id: null })
                    this.atualizarTela();
                })
                .catch(err => {
                    console.log(err);
            });
        }
    }

    cancelar() {
        this.setState({ estado: 'LISTAR' })
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

    callAlterarApi = async (id, curso) => {
        
        const requestOptions = {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(curso)
        };
        
        const response = await fetch(this.api + '/' + id, requestOptions);
        if (response.status !== 200) throw Error();
    }; 
  
    fecharModais() {        
        this.setState(
            {
                id: null,
                nome: '',
                modalDelete: false,
                modalFinalizar: false
            });
    }       
  
    abrirModelDelete(id, nome) {
        
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
                this.atualizarTela();
            })
            .catch(err => {
                this.fecharModais();
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

    callCriarApi = async (curso) => {
        
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(curso)
        };
        
        const response = await fetch(this.api, requestOptions);
        if (response.status !== 201) throw Error();
    };

    iniciarCurso(id) {
        this.callIniciarCurso(id)
            .then(() => {
                this.atualizarTela();
            })
            .catch(err => {
                this.handleClose();
                console.log(err);
        });
    }

    callIniciarCurso = async (id) => {
        const response = await fetch(this.api + '/iniciar/' + id);
        if (response.status !== 200) throw Error();
    }

    finalizarCurso() {
        this.callFinalizarCurso()
            .then(() => {
                this.setState({nota: 0, modalFinalizar: false});
                this.atualizarTela();
            })
            .catch(err => {
                this.handleClose();
                console.log(err);
        });
    }

    callFinalizarCurso = async () => {

        const {id, nota} = this.state;
        const response = await fetch(this.api + '/finalizar/' + id + '/' + nota);
        if (response.status !== 200) throw Error();
    }

         
  
    abrirModalFinalizar(id, nome) {
        
        this.setState(
            {
                id: id,
                nome: nome,
                nota: 0,
                modalFinalizar: true
            });
    }

    render() {

        return (
            <>
                <Card>
                    <Card.Header as="h5">Cursos</Card.Header>
                    <Card.Body>
                        { this.state.estado === 'LISTAR' && (
                            <>
                                <Button variant="success" onClick={() => this.criar()}><AiOutlinePlus /> NOVO</Button>
                                <Table striped bordered hover>
                                    <thead>
                                        <tr>
                                            <th width='30%' >Nome</th>
                                            <th width='25%' >Instituição de Ensino</th>
                                            <th width='15%' >Status</th>
                                            <th width='10%' >Nota</th>
                                            <th width='20%' >Ações</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        {this.state.cursos.map((curso, index) => {
                                            const { id, nome, instituicaoEnsino, status, nota } = curso;
                                            return (
                                                <tr key={index}>
                                                    <td>{nome}</td>
                                                    <td>{instituicaoEnsino.nome}</td>
                                                    <td>{status}</td>
                                                    <td>{nota}</td>
                                                    <td> 
                                                        <OverlayTrigger
                                                            placement="right"
                                                            delay={{ show: 250, hide: 400 }}
                                                            overlay={<Tooltip id="button-tooltip-2">Editar</Tooltip>}
                                                        >
                                                            <Button variant="primary" onClick={() => this.editar(id)}><FaRegEdit /></Button>
                                                        </OverlayTrigger>
                                                        <OverlayTrigger
                                                            placement="right"
                                                            delay={{ show: 250, hide: 400 }}
                                                            overlay={<Tooltip id="button-tooltip-2">Visualizar</Tooltip>}
                                                        >
                                                            <Button variant="info" onClick={() => this.visualizar(id)}> <FaRegEye /> </Button>
                                                        </OverlayTrigger>
                                                        { (status === 'PENDENTE' || status === 'REPROVADO') && (
                                                            <OverlayTrigger
                                                                placement="right"
                                                                delay={{ show: 250, hide: 400 }}
                                                                overlay={<Tooltip id="button-tooltip-2">{status === 'PENDENTE' && ('Iniciar Curso')} {status === 'REPROVADO' && ('Reiniciar Curso')}</Tooltip>}
                                                            >
                                                                <Button variant="secondary" onClick={() => this.iniciarCurso(id)}> {status === 'PENDENTE' && (<FaArrowRight />)}  {status === 'REPROVADO' && (<FaUndoAlt />)} </Button>
                                                            </OverlayTrigger>
                                                        ) } 
                                                        { (status === 'INICIADO' || status === 'REINICIADO') && (
                                                            <OverlayTrigger
                                                            placement="right"
                                                            delay={{ show: 250, hide: 400 }}
                                                            overlay={<Tooltip id="button-tooltip-2">Finalizar Curso</Tooltip>}
                                                            >
                                                                <Button variant="secondary" onClick={() => this.abrirModalFinalizar(id, nome)}> <FaCheck /></Button>
                                                            </OverlayTrigger>
                                                        ) }
                                                        <OverlayTrigger
                                                            placement="right"
                                                            delay={{ show: 250, hide: 400 }}
                                                            overlay={<Tooltip id="button-tooltip-2">Excluir</Tooltip>}
                                                        >
                                                            <Button variant="danger" onClick={() => this.abrirModelDelete(id, nome)}> <FaTrashAlt /> </Button>
                                                        </OverlayTrigger>
                                                    </td>
                                                </tr>
                                            )
                                        })}
                                    </tbody>
                                </Table>
                                <Pagination className="justify-content-md-center">
                                    <Pagination.First />
                                    <Pagination.Prev />
                                    <Pagination.Item>{1}</Pagination.Item>
                                    <Pagination.Next />
                                    <Pagination.Last />
                                </Pagination>
                            </>
                        )}
                        { this.state.estado !== 'LISTAR' && (
                            <>
                                <Form>
                                    <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
                                        <Form.Label>Nome</Form.Label>
                                        <Form.Control type="text" name="nome" onChange={this.handleInputChange} value={this.state.nome}  disabled={this.state.estado==='VISUALIZAR'} />
                                    </Form.Group>

                                    <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
                                        <Form.Label>Descrição</Form.Label>
                                        <Form.Control as="textarea" rows={3} name="descricao" onChange={this.handleInputChange} value={this.state.descricao} readOnly={this.state.estado==='VISUALIZAR'}  />
                                    </Form.Group>

                                    { this.estado === 'VISUALIZAR' && (
                                        <>
                                            <Row>
                                                <Col>
                                                    <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
                                                        <Form.Label>Data de Início</Form.Label>
                                                        <Form.Control type="text" name="dataInicio" onChange={this.handleInputChange} value={this.state.dataInicioFormatada} readOnly />
                                                    </Form.Group>
                                                </Col>
                                                <Col>
                                                    <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
                                                        <Form.Label>Data de Finalização</Form.Label>
                                                        <Form.Control type="text" name="dataFim" onChange={this.handleInputChange} value={this.state.dataFimFormatada} readOnly/>
                                                    </Form.Group>
                                                </Col>
                                            </Row>
                                        </>
                                    )}
                                                       
                                    <Row>
                                        <Col>
                                            <Form.Group className="mb-3" controlId="formInstituicao">
                                                <Form.Label>Instituição de Ensino</Form.Label>
                                                <Form.Select aria-label="Instituição de Ensino" name="idInstituicao" value={this.state.idInstituicao} onChange={this.handleInputChange} disabled={this.state.estado==='VISUALIZAR'} >
                                                    <option value={null}>Selecione uma Instituição de Ensino</option>
                                                    {this.instituicoes.map((instituicao) => {
                                                        const { nome, id } = instituicao;
                                                        return (
                                                            <option value={id}>{nome}</option>
                                                        )
                                                    })}
                                                </Form.Select>
                                            </Form.Group>
                                        </Col>
                                        { this.state.estado === 'VISUALIZAR' && (
                                            <>
                                                <Col>
                                                    <Form.Group className="mb-3" controlId="formInstituicao">
                                                        <Form.Label>Status</Form.Label>
                                                        <Form.Select aria-label="Status" name="status" value={this.state.status} onChange={this.handleInputChange} disabled={this.state.estado==='VISUALIZAR'} >
                                                            <option value={null}>Selecione um Status</option>
                                                            {this.statusList.map((status) => {
                                                                const { label, value } = status;
                                                                return (
                                                                    <option value={value}>{label}</option>
                                                                )
                                                            })}
                                                        </Form.Select>
                                                    </Form.Group>
                                                </Col>
                                                <Col>
                                                    <Form.Group className="mb-3" controlId="exampleForm.notaVisual">
                                                        <Form.Label>Nota</Form.Label>
                                                        <Form.Control type="number" name="nota" onChange={this.handleInputChange} value={this.state.nota} readOnly  />
                                                    </Form.Group>
                                                </Col>
                                            </>
                                        )}
                                    </Row>
                                    
                                </Form>
                                <br />
                                <div className="text-right" >
                                    { this.state.estado !== 'VISUALIZAR' && (   
                                        <Button variant="primary" onClick={() => this.salvar()}>SALVAR</Button>
                                    )}
                                    <Button variant="danger" onClick={() => this.cancelar()}>VOLTAR</Button>
                                </div>
                            </>
                        )}
                    </Card.Body>
                </Card>                
  
                <Modal show={this.state.modalDelete} onHide={() => this.fecharModais} animation={false}>
                <Modal.Header closeButton>
                    <Modal.Title>Exclusão de Curso</Modal.Title>
                </Modal.Header>
                <Modal.Body>Deseja realmente deletar o curso {this.state.nome}!</Modal.Body>
                <Modal.Footer>
                    <Button variant="danger" onClick={() => this.handleClose()}>
                        Cancelar
                    </Button>
                    <Button variant="primary" onClick={() => this.excluir()}>
                        Confirmar
                    </Button>
                </Modal.Footer>
                </Modal>
                               
  
                <Modal show={this.state.modalFinalizar} onHide={() => this.fecharModais} animation={false}>
                <Modal.Header closeButton>
                    <Modal.Title>Finalizar Curso</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form.Group className="mb-3" controlId="exampleForm.nota">
                        <Form.Label>Nota</Form.Label>
                        <Form.Control type="number" name="nota" onChange={this.handleInputChange} value={this.state.nota} />
                    </Form.Group>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="danger" onClick={() => this.fecharModais()}>
                        Cancelar
                    </Button>
                    <Button variant="primary" onClick={() => this.finalizarCurso()}>
                        Confirmar
                    </Button>
                </Modal.Footer>
                </Modal>

            </>
            
        )
    };

}
export default Curso;