/*
 *  Copyright 2021 The original authors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.galiglobal.advent.year2021;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestChallenge10_1 {

    @Test
    void testExample() {
        assertEquals(26397, Challenge10_1.getTotalSyntaxErrorScore(example));
    }

    @Test
    void testExample0() {
        assertEquals(0, Challenge10_1.getTotalSyntaxErrorScore(example0));
    }

    @Test
    void testChallenge() {
        assertEquals(392421, Challenge10_1.getTotalSyntaxErrorScore(challenge));
    }

    private static final String example0 = """
            [({(<(())[]>[[{[]{<()<>>""";

    private static final String example = """
            [({(<(())[]>[[{[]{<()<>>
            [(()[<>])]({[<{<<[]>>(
            {([(<{}[<>[]}>{[]{[(<()>
            (((({<>}<{<{<>}{[]{[]{}
            [[<[([]))<([[{}[[()]]]
            [{[{({}]{}}([{[{{{}}([]
            {<[[]]>}<{[{[{[]{()[[[]
            [<(<(<(<{}))><([]([]()
            <{([([[(<>()){}]>(<<{{
            <{([{{}}[<[[[<>{}]]]>[]]""";

    private static final String challenge = """
            <<{<[<<<{<<(<({}())({}[]))[[{}[]]<{}[]>])[<[{}[]]{()()}>{((){})<<><>>}]>>[<(<<{}>>[<()()><<><>>]
            {[[[{<[(([[{(({}))([<>[]]((){}))}<<<[][]>[{}{}]>[{()[]}(()())]>][[[{<>()}[{}[]]]]]]<{{{{()[]}}}[[{[][]}{{
            ([((<(<(<<([{{[][]}([]<>)}({<>{}}[[]()])]<[[()()](()[])]([[][]](<><>))>>{{<(<><>)[<>()]>([[]()])}<<[[]()]<[][
            {{{([((([({[{{()()}{<>[]}}({{}{}}([]{}))]{([{}<>])<(<><>){[]<>}}}})({{{(()())[[][]]}{<{}{}>
            {{([[((({(({({<><>}([]{}))([()()]{[]{}})}[[({}{})<(){}>]<<()<>>>]){[[[()()][<>[]]}<[{}()]{{}<>}
            {([<[[<<({[<(<()<>>(()())){<<>[]><()[]>}>[({{}<>})]][[(<()<>>{(){}})[{[]<>}[[]{}])][[(()()){[][]}]<{{}{}}<[
            [<{[((<([[([<<{}()>{{}()}>{(()[])[<>{}]}]<{[<><>]{<>{}}}>)]{[{{{()<>}[{}<>]}<<()>{{}{}}>}]({{<
            [<((<<({[{<{[[<><>](<>{})]{<()()><[]{}>}}<<[{}()]<[][]>><({}<>)>>>{<[[()<>]<{}[]>]<(<>()){<>{}}>>[{(()[])[{
            ([(<<{{<[<(<<[[]{}]({}{})><[()[]]{<><>}>>[[{[]<>}[{}()]][({}{})({}())]>){(<{(){}}[{}[]]>[{[][]}<<
            ([<{<<<[[<{[{({}{})[{}[]]}])[[(({}{}){()<>})<[()[]]<[][]>>]<(<()()>{()()}){(<><>)([]())}>]>]]<<{
            [{((<<{[{[<<<(()[])[[][]]>>><{<{<>[]>{[]{}}>{<<>()>[<><>]}}[{{<>{}}([]())}{({}[])[<><>]}]>][(<({<>()}[[]{}])[
            <(<[[<{(<[(<[([]())]({<>()}({}()))>[({(){}})[[[][]][<>{}]]]){{[[<><>]{{}[]}]<[(){}]<{}()>>}(<(<>{
            {{{([[(<<<{[{(<><>){<>[]}}<<[][]><()<>>>]<[[(){}][<>[]]]{{()<>}[{}()]}>}[{{({}<>)}[[{}{}]{{}[]}]}[([()()]
            {(<[{[{([({[[{<>}]{[<>{}]([][])}]{[(<>[])({}())]<{<><>}({}<>)>}}<[[(<>()){()[]}][<()()>{[]{}}]]<{[[]][[]()]
            <{(({({(<{{<{{()()}<()[]>}{[[]()](<>[])}>}<<({{}()}<[]{}>)[{[]()}]><<<<><>>[<>[]]>>>}([<[{()[]}(()()
            [[[{<({{[[<[{[(){}]<[]<>>}[[<>[]]{()<>}]]({<{}[]>[<>[]]}{({}{})<{}[]>})>{[[{[]{}}{<><>}]{(
            <([<[(([[[[([<[][]>]<([]()){<>()}>){[{[]()}([][])]<{[]<>}({}<>)>}]<[[[[]<>][<>{}]]{{{}()]{<>(
            <({{<<<{<<[[[<()<>>[()[]]]](<[{}<>]{[][]}>[{<>()}<()()>])]><{<[<()<>>[[]()]]<<[][]><()()>>>
            [[{{(([[<{[[(<[][]><<><>>}[{{}()}]]{(<<><>>({}<>))[[[][]]<[]{}>]}]<[{({}<>)[[]]}({()()}[<><>
            ([<[([[<{<((<<{}()>[<>()]>)(<{{}()}>(<[]<>>{()()})))>({<<{(){}}{{}}><[<>{}]<<>{}>>>{([(){}](<>)){{()<>}({
            [{[[<((({[{[<[[]()]<{}[]>>{<{}<>>(<>[])}]<{({}{}){{}[]}}{[[]<>]([][])}>}(<{(<>)[()<>]>>(({{}()})[[{}[]]{[]}
            [{[[({<<{([{{({}())[()[]]}<[<>()]({}{})>}({[{}<>]<<><>>}{{[]<>}{()]})][<([<>()]({}[])){[{}{}](<>[])}>[((()
            {{((<[{{[[(<{({}()){()<>}}[{{}<>)({}{})]>({<(){}>[[]{}]}[[[]()]<{}>]))<<{[[]{}]({}[])}{<()>({}
            <{<({[[({<(<<<[][]>[<>[]]><(()[])>>{([[]<>]({}{}>)})((<[{}][{}<>]>{(()<>)})<<<<>[]>[(){}]>[(<>{})<<>[]>]>)>}
            [[[{[[{<{({<({{}<>}({}<>))>{[({}())]}})[<[[([][])][<{}<>><()<>>]]([[<><>]<[]()>]<({}{})(<><>
            [<({{[(<{(<<((<>)<{}[]>){{<>[]}<{}{}>}>(<(<><>)<{}<>>><{{}()}[{}()]>)>([([[]{}][{}[]])]))}>((({{
            <<{({{(<([[(<[<>()]<<><>>>(<[]()>{{}<>})){{<{}[]>[()()]}<(<>[])<()[]>]}]{({<(){}>{[]()}}[(<>
            (({<[({{[[{{[[()[]]([][])]{(<>[])<(){}>}}<<({}{})([]())><({}[])>>}<<<<{}[]>{[]{}}>(<[][]>[{}{}])>(({()<>
            {[[([<[{[{<[[({}[])<{}[]>]([<>[]]({}{}))][({()[]}(()()))]>{<<({})([]{})>{[[]()](()()]}>[(((){})(()[]))<[[]<>
            ({[(<[(<({<[[[{}()]][<()()>[()[]]]]<{{<>[]}}[[[]{}]([]<>)]>>})<<{[<[()[]](()()}><(()())[<><>]>
            ((<[{([[{(([([<>()][[][]])]))}]])<{(<<{{<<[]{}>({}())>}}>>[{([[{{}()}{(){}}]](((()[])[(){}])<[<><
            {(<{[{<<(<<[<<{}{}>><[[][]}{(){}}>]{{{[]<>}[[]<>]}}>{({<{}<>>({}<>)}<<[]<>>>)[(<{}<>>(<>))<[
            [<({<<({[{[<[([]<>)]{[(){}]({})}>{{((){})}[<[][]>]}]({(<{}<>>(<>())){{[]()}(()())}}(((<>{}){()()}){{[]()}<<>
            [[{{[<<[[[<<[{{}[]}<(){}>][{(){}}<{}[]>]>>(({({}{}}{<>{}}}([<>[]](()[]))))]{[[{((){})<<>()>}{{()[]}{<>()}}]<
            {(([([<({[[{{[<>[]][<>[]]}[([][])[{}()]]}[([()()][{}()])([<>{}])]]][[{[([]{}){[]()}][<(){}
            <{{[{<(((({[{<[]()>({}{})}]}))<[((<([]<>)[{}[]]>{<(){}>[[][]]}))[{<[()()][{}()]>(({}[])<<>[]>)}[((<>()){{}
            [(<[{[[<{[([{{[]()}<()>}(([]())<(){}>)][(({}{})<{}<>>)])(<({{}()}({}))<[()[]]{<>[]}>><(<{}<>><{}<>>)<({}<>
            {([({<<((({[({[][]})<{{}{}}<{}()>>]}<(<<[]()><()<>>>{{[]<>}([][])})(<{[]<>}[[]<>]>)>){([{<[]()>[<>[]]}[{<><
            [{{<<[<<<<{<({[]}[()[]]){{{}<>}[(){}]}>({([]())({}[])>)}><[[{[()<>]({}{})}<<<>{}>{()[]}>]{{[[][]]}(<<>()><
            (({({[{({[{[{([][])[[][]]}]{{{[]()}({}[])}({<>[]}(<>))}}[<<([]<>)>(<{}{}>{()})>[{[<><>][<>{}]}]]]}([{
            (({{<[{[([({([{}{}][{}<>])<<{}{}><<>()>>})]([((<[]()>[{}()])<<{}<>>((){})>)]<{[[{}<>]<[][]>]<(()[]
            {<[({<[[<[(<<({}[])[[]()]>([<>{}]{{}<>})>[<<{}[]>{<>()}><[<>{}]({}[])>])]{{{({{}{}}<[]()>)
            {<(({<[({<<[[[()[]]<{}<>>]]>><[{<{<><>}(<><>)>[[()[]]{<><>}]}[[{<><>}{{}<>}]({{}{}}[[][]])]]<<[{{}{}}<<>[]>]
            [<{({{(({([{(<{}[]>(<>{}))<[[]{}](()[])>}<{(<>{})(<>{})}>])<<<<({}<>){<>{}}>[(()()>(()())]>
            ({((({[(<{{({({}())[[]<>]}([<>[]]{{}()}))<({{}()}<{}{}>)[{[]{}}({}[])]>}[([{{}<>}<{}>])[{{()[]}{{}()}}[([]{
            [<[{<{([<({<[[{}<>]{[][]}]{<<>{}><<><>>}>({[[]{}]<{}<>>}{{{}<>}(()[])})}){{{[<[]()><[][]>]
            <<{<{[[{([{<[<{}[]>{[]<>}]<<{}{}>[{}[]]>>}([([{}{}]{<><>})[{<>{}})])]){{([[<{}[]>({}[])][[<><>]
            ([[(<([[[<[((([]{}){[]<>}))<{<[][]>[[]()]}[(()<>)<[]{}>]>]{[([[][]][()[]]){{(){}}[{}<>]}]}><
            <[{{<{<({{([[[[]{}]<{}()>><([]()){[]{}}>]<{<()[]>([]())}>)}<<{{((){}){[][]}}(({}<>)(()()))}([{[
            ({{({[[{<({[({<>{}}[<>()])(([][]){{}{}])][({{}<>}(()[]))[<[]()><[]{}>]]}[{(<()[]><[]()>)[<{}()>{()[]}]}
            <<<{<{[([[[[(<()[]>){<[]()><[]()>}]<<([])<[]<>>><[[]<>][<>[]]>>]<<[[()()](<><>)]({()()}[<>[]])>([<[]
            ((([{([({({<(<{}()>[()<>])<[{}<>]<{}[]>>>][{<[[][]]{{}()}>[({}()){[]<>}]}([{{}{}}[[]()]])])<[(({<>()}
            ([<<[(<{<<[<[{()[]}{()[]}](({}[]){{}[]})>]{([{()[]}])({{<>()}(()())})}>((({{()()}(()<>)}][[(<>()){()}
            ({[{[((<[<{<([[][]]{{}()})[{<>()}(<>{})]>{{{{}}}{{()}({}[])}}}>]<(<{<(<>())(()())>}>[<{[{}<>]{{
            {({[<{<[[[[<({<>{}}{()[]}){<[]()><[]()>}>([(<>())[[]()]](({}{})[()<>]))]]({([[<>{}]<{}[]>](<()()><()<>>))
            ([<[<{<<<<((({<>}[<>{}])<({}())<()<>>>)[[[[]()][{}{}]]<(()<>)[<>]>]){{[({}())][([][])<()[]>]}{
            [{[({{<[{[<<[[<>()]]<([]())>><<<[][]>>{[()<>]{{}}}>>]}]{<{{{{[<>{}]({}[])}<{{}}{<>{}}>}{<({}){<>
            {<<{{({([{[([{[]<>}{<>[]}](<()[]>))[([{}]<()<>>)<<<>[]>[()<>]>]]}{[{<[()[]][{}{}]>{[[]()][{}[]]}}({[[]{
            ((<(<{{<([{[{{()[]}{<><>}}{{<>}({}{})})[([(){}][[][]])[<()[]><(){}>]]}<([(<>()){[][]}][([]())([]())])[
            <<{[{{<{<[[[<<()<>>[()[]]>([()]{{}[]})]](<[(()<>)[()[]]]>{<[{}{}]>[<<><>><<><>>]})][{((<{}{}>[()
            <<<[[<[[([{[(<()><[]{}>)[(()[])[[]{}]]]{[{{}<>}[{}<>]]}}[(<<<>()><()[]>>[({}())<(){}>])[[[[][]][<
            [[<(({[(([{{<([][])[[][]]>[{{}{}}{<>[]}]}<[(<><>)](<[][]>[<>{}])>}]{{{([(){}]([]))<[{}<>](())>}([[(
            [<{[([<{({([((()>[{}[]])[(<><>){[][]}]])([{([]<>){[][]}}<(()[]){[]()}>]<((<>{}){[][]})({{}()}<()[
            ((((<<<({([<{[{}()]{[]{}}}{({}[]){(){}}}>[[{()[]}[<>()]]<(()<>){(){}}>]]{[<(()())[{}[]]>(<<>()>({}
            (<({((((<{{{[{{}{}}[(){}]][{<>{}}{(){}}]}([[{}[]]{<><>}]<[[]{}]<[][]>>)}({[[{}<>]<(){}>]}<{{<>[]}}<<<>[]>
            {(<{(<{({(([{{()()}}][(({}{})<{}>)])<<(<()<>>{{}<>})(((){})[[]{}])><[[<>{})[<><>]][{()()}]>
            <{[(<[[{(<({[<{}()>{{}{}}]{(()){[]{}}}}[{{[]<>}[{}{}]}])({({[][]}{[]()})})>(<{{(()[])<<>>>[<<>[]><[]<
            ([<[<[[{<[<(([<>{}]){[(){}]<()()>})<{([]{}}<{}<>>}{<{}{}><[]()>}>>(<<{<><>}(<>())>({<>}[{}{}])>(<(<><>)
            ([{[[(<[(([{[{<>[]}{<>()}]<[(){}](<>{})>]<<<<>[]>>{(<><>)<[]{}>}>])){{{{<([]<>)([]{})><{{}{
            [<{({{(({<[([[()<>]{[][]}]({<>[]}(()[])))](<<({}())([][])>[{()<>}(()())]><{[<>{}]<<>()>}[[{}()]{<><>}]>)>{
            {({{{{[{{[<{{<[][]>([])}<[[]{}][{}()]>}>]{{[[<<>[]>[[][]>]<<{}<>>(()<>)>]{[[<>{}]{{}[]}]}}<[[[<><>]<<
            <({{({(({{<{{([]{}){<><>}}<(<>())({}{}]>}[{((){})(<>())}]>(({<[]()>([]{})}({{}{}}<<>()>)))}[[([<{}<>>
            [[<{<{<((<<({(<>())(<>())}{{(){}}[{}()]})<(<{}<>>{(){}})>>{{({[][]}{{}[]})<<{}<>>(()<>)>}<<<<>()>>{({}{}){()
            {[(<<<{(<{<<({<>{}}{()[]}>({<>[]}({}()))>>}>)<{(<(<({}[]){()[]}>)>((({()[]}[[][]]){<[][]><<>()>})[(([]<>)<
            <[<[{<[{[[<([<{}()>[<>]]{[[][]]<{}()>}}>({{{<>[]}{()[]}}{{()}<<><>>}}<<([])([][])>{[{}<>]((
            {[<<[[(<<{[<[[[]]({}())]([{}()][[]<>])>]}<({{(()[])<[][]>}{({})<()<>>}})([((()[])<[]<>>)<{(){}}([]())
            <{{<[[[(((<[{({}()){()()}}(<{}()>{()<>})]{{{()()}}{[[]{}]({}())}}>))<{<(((()<>)[{}[]])){[<[]<>>{{}()
            <([((<<(((({{[{}<>]<[]<>>}(<[]{}><[]{}>)}[{{<>[]}}{(<>{}){[]<>}}])<<{(()())<<>{}>}[[{}[]][{}()]]>[<[[]()
            {({{({[[({[<[<<><>)<{}{}>][<(){}>{[]{}}]>({(<>{})(<>())}<({})({})>)]})]<({[[({[]<>}{()()}){({}()
            ((([[(<(<[([({{}()})[{{}()}({}{})]]{({[]()}{{}()})(<()[]><<>[]>)}){{([{}()])}}]<[(<(<><>)[()[]}>[{{}<>}])]>>[
            {(([(<[[[[<<([(){}][<>[]]){({}())[[]{}]}>>[[<{()[]}{(){}}>(<{}{}><{}{}>)]<([()[]]<{}>)(<()[]>{
            {({([(<{<[[<<[<><>]<<><>>>>[<<{}<>>[{}<>]>{[[]{}][{}<>]}]]][{{<[{}[]]({})><[()()]>}}<[<{[]<>}[[]()]>]{[
            (((<{([<[(<[{([][])<[][]>}({{}<>}[{}{}])]>[[({[]<>}{<>{}})]})[{({(()[])([][])}{<()()>[[][]
            <(<{{<([<(<{<{{}}{<>{}}>}([([]{}){(){}>]{<()<>>([])})>)<<({<[]<>>})({[()[]]}<[(){}]{{}()}>)>{[<<[][]>>({[]{
            ({{(<(([[{((<<()[]><{}<>>>])}][<({<[{}[]][{}{}]><(()())([]())>})>{<(<{[]{}}{{}<>}>{{[]<>}{<><>}})>}]]{<[{[[
            {<[<([(((<[{[(())]}[(<{}()>)([[]][{}])]]{({<[]{}><()[]>}{{[]()}<{}<>>})}>){(({{<(){}>{<>[]}}(<(){}>(
            ([[{<{{{({{<[<[][]><()()>>><<[<>()]>>}{[[(())]<{[]<>}<()<>>>]}})(<({[([]<>)<<>[]>]({<>()}[
            {<[<<[((<<{<{([]<>)(<><>)}([()<>][{}[]])>}<<{{[]()}(<>{})}([<><>]({}[]))>{<[()<>]<{}()>>{<()(
            {<{(<[[[<[{<[({}())<()<>>]>[{([]{})([]<>)}[<[][]>]>}][<([[()<>]{[][]}]){<[[]()](()())>({()}(()()))}>({<[()<
            [{({<<<({{<[(({}[]){{}()})[{[]{}}{<><>}]]<{<[][]>(<>())}([<>{}])>>}[(<{{<>}([][])}>{{[[]()][[][]]}{<<>()><
            <<([{{[(<[([<{[]()}<<><>>><(()())<()[]>>][{<[]()><{}()>}{<<><>>[()<>]}])(<<<<>{}][{}[]]>[{<>}({}
            ((<<<<[{<[[<[{()()}<[][]>]{(()<>)}><{[[]{}]<{}{}>}>]<<{{[]<>}([])}{({}())([]{})]><[(<>())(<>{})]>>]><([<(([]
            {[[<(<[({[[[{{<>()}<()()>}]]]}[({(<[[][]]>(<{}<>>))}[<{<[]{}>(()<>)}{<{}{}>[()<>]}>])(<{([[]<>]){
            {{<<{([<{[[[(<{}{}>[[]<>])({<><>}{{}[]})]]][([([()[]])[(()<>)<[]{}>]]<{{()<>}{[]()}}<<[]{}>{
            <(([[<(({{(<{{()<>}[<>()]}[({}())[[][]]]>{{[{}<>]((){})][<<>{}><[]{}>]})<<({{}()}<<>{}>){(<>())[<>{}]}>(
            (<[(<<(((([<[<{}[]>][[()()]<<>()>]><[<{}{}>{()<>}](<()()])>][{{[{}<>]{<>()}}[<()()><{}>]}<(<{}{
            ({{({[(<[{[<[((){})({}{})]([<>[]][[]()])>](<(<(){}><()()>)({<>{}}<{}()>)><{{<>()}(())}<{()()}<[]()>>
            {<([(<<{([[[[(()<>)(()())]][[([]{})({}())]<[[]<>]{()[]}>)]([[<(){}>{<>{}}]((()())[()()])])]
            (<<(((<[[{[{<[()<>]{<>{}}>([[]()][()()])}]{([<[]<>>{<>[]}]<<[][]>[{}()]>){<{[][]}[<>{}]><[<>[]]({}{}
            <([[[(<[([{((<<>()>{()[]}){(<>())([])})[{<{}[]>([][])}[({}[])[<>()]]]}[(<<<>{}><()<>>><<<>[]>{(
            [[{<[[(<<<(([(<><>)([][])](([]{}){[]{}}))[<[[]{}]>([[]()]{[][]})])>{{{[<()[]>[[]{}]][{()<>}[<>[]]]}}
            ([(<{<<(<{{{[[<>[]]<(){}>]([()[]][()[]])}{((<>()))}}{<[<{}<>>(<><>)](([][])<<>()>)}}}(<<[<{}[]
            <[{{({{<[<[{(<<>[]>(<>()))<([]<>)[{}<>]>}]>{<<{<[]{}>(<>())}>([<(){}><(){}>]{[<>()]([]())})>}]>}})}}{(((
            [<(((<{<<[(<{(<>[])}>){(<<{}<>>([]<>)>{<{}()>}}[[<{}{}>{[][]}](<<>[]>{{}[]})]}]><<([<[(){}]<()>><[(){}]
            ({[[(<[{(<((<[()[]]{[]<>}>){<(<>){()<>}>})(({<(){}>[{}[]]})[[(()[]){[]<>})<{[]()}>])>)<<<(<({}())(<>[])
            [{([<<<[{{([<<{}{}>>[{{}[]]]])}<<[<{{}}{<><>}>(({}{})[<>[]])]({[<><>]([][])}{(()<>)<<><>>})
            [[<(<<[{([[[[<[]{}><[]()>]][{[[][]]<()<>>}]]]{{{(<[]{}>(()<>))<(<>[])<<><>>>}]({{(()[]){{}()}}
            ({<<<{[<[[[{([{}<>]{<>()})(<<><>>)}(<(<>[])<{}>))][({<{}[]>[[][]]}(([]())(()[])))<<[[]()]{<><>}>>
            [{<[[{{[<<([([<><>]([]<>))]({[()[]][{}{}]}{{()()}[[]<>]})){(<(()[])<[]()>><[[]()]<()<>>>)(<<()(
            {<({((<([<(({{[]<>}[{}<>]}[<()[]><[]{}>]){<{[][]}{<><>]>})(({({}())[()()]}{[{}()][[]<>]}))>])<(<{(<{
            """;
}
