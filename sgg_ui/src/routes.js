import React from "react";
import { Switch, Route, BrowserRouter } from "react-router-dom";
import Usuario from "./views/Usuario";
import Livro from "./views/Livro";
import Curso from "./views/Curso";
import Instituicao from "./views/Instituicao";
import Anotacao from "./views/Anotacao";

const Routes = () => {
   return(
       <BrowserRouter>
            <Switch>
                <Route path='/' exact Component={Curso} render={() => < Curso />} />
                <Route path='/livros' Component={Livro} render={() => < Livro />} />
                <Route path='/anotacoes' Component={Anotacao} render={() => < Anotacao />} />
                <Route path='/cursos' Component={Curso} render={() => < Curso />} />
                <Route path='/instituicoes' Component={Instituicao} render={() => < Instituicao />} />
                <Route path='/usuarios' Component={Usuario} render={() => < Usuario />} />
            </Switch>
       </BrowserRouter>
   )
}

export default Routes;