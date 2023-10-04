REGRAS DE NEGÓCIO:
- TODAS AS TABLES TEM DELETED_AT, REASON_ALTERACAO
- NAS ROTAS GET SÓ RETORNAR AQUELES QUE DELETEDAT == NULL
- TODA OPERAÇÃO NO BANCO DE DADOS DEVE LEVAR O USERID QUE FEZ AQUILO

**DONE**

CREATE USER - OK!
SISTEMA DE LOGIN - OK!
SISTEMA DE AUTENTICAÇÃO POR JWT - OK!
SISTEMA RECUPERAÇÃO DE SENHA - OK!

**TO BE DONE**

USER
I. validar:
- confirmar o que deve ser restrito ao admin

II. to do:
*confirmar e informar à auto tech*
- alterar sistema de reset password {token ficará em cache, de alguma forma será armazenado para fazer a requisição}
- DELETE USER - [rota exclusiva adm]
- LIST USER {
- pageable padrão por nome
- byId
  }
- CRIAR ADM {
- rotas para adm, primeiro cria manualmente e com o acesso adm, ele cria novos adm

CRIAR TABLE SAÍDAS *(CONFIRMAR CAMPOS GASTO)*

I. validar:
- campos gasto 
- roll taxativo de gastos fixos

II. to do:
- enum com tipoDeGasto {fixos {roll taxativo, OUTROS_GASTOS}}
- SOFT DELETE
- CREATE SAIDA
- UPDATE SAIDA - tem que levar o "REASON_ALTERACAO", string que o user explica o por quê de estar alterando
- LISTAGEM DE SAIDAS {list by date, e padrão por última inclusão no DB }

CRIAR TABLE ENTRADAS *(CONFIRMAR CAMPOS ENTRADA)*

I. validar:
- confirmar se precisa de enum com vendas recorrentes {produtos/serviços "padrões"}
- {valor, data, fonte de entrada}

II. to do:
- tem que conferir a fonte de entrada, e realizar  cálculo de "valor" seguindo a regra de negócio
- SOFT DELETE
- CREATE ENTRADA
- UPDATE ENTRADA - tem que levar o "REASON_ALTERACAO", string que o user explica o por quê de estar alterando
- LISTAGEM DE ENTRADAS {list by date, e padrão por última inclusão no DB }

CRIAR TABLE SALDO

I. to do:
- vai tomar o acumulado quando fechar o caixa e salvar cada dia.
- pode no dia iniciar negativo se user tomar dinheiro antes, mas nunca afeta o registro
- no fechamento de caixa salva o id do user, o valor, o horário
- ROTA SOMENTE PARA CRIAR, EDITAR E LISTAR
- UPDATE SALDO - tem que levar o "REASON_ALTERACAO", string que o user explica o por quê de estar alterando em saldo fechado
- LIST SALDO - list by date, e padrão por última inclusão no DB

RELATÓRIOS ESPECÍFICOS ADMINISTRAÇÃO

I. validar:
- AGUARDANDO ADM SOLUÇÕES retornar especificidade dos relatórios

II. to do:
- rotas com a regra de negócio já estabelecida no backend
- exportar relatórios será uma query ao banco de dados com os parametros recebidos e: SUM / GROUP BY
- IMPORTAR HISTÓRICO DO DB JÁ EXISTENTE

```
@Controller
@RequestMapping("/financeiro")
public class FinanceiroController {
private final AtomicBoolean caixaAberto = new AtomicBoolean(true);

    @PostMapping("/fechar-caixa")
    public ResponseEntity<String> fecharCaixa() {
        if (caixaAberto.get()) {
            caixaAberto.set(false);
            return ResponseEntity.ok("Caixa fechado com sucesso.");
        } else {
            return ResponseEntity.badRequest().body("O caixa já está fechado.");
        }
    }

    @PostMapping("/pagamentos")
    public ResponseEntity<String> processarPagamento(@RequestBody String pagamento) {
        if (caixaAberto.get()) {
            // Lógica para processar pagamento
            return ResponseEntity.ok("Pagamento processado com sucesso.");
        } else {
            return ResponseEntity.badRequest().body("O caixa está fechado. Não é possível processar pagamentos.");
        }
    }

    @PostMapping("/vendas")
    public ResponseEntity<String> processarVenda(@RequestBody String venda) {
        if (caixaAberto.get()) {
            // Lógica para processar venda
            return ResponseEntity.ok("Venda processada com sucesso.");
        } else {
            return ResponseEntity.badRequest().body("O caixa está fechado. Não é possível processar vendas.");
        }
    }
}
```

FAZER SOMENTE UMA CONSULTA AO DB E RETORNAR A SOMA DOS VALORES NO PERÍODO ESPECIFICADO
SELECT SUM(valor) AS total
FROM sua_tabela
WHERE data >= '2022-11-10' AND data <= '2022-12-10';


O GROUP BY é usado para agrupar os resultados de uma consulta SQL com base em uma ou mais colunas. 
Aqui está um exemplo simples usando uma tabela fictícia chamada "vendas" para ilustrar o uso do GROUP BY:
Suponhamos que temos a seguinte tabela "vendas" com dados de vendas:


+----+------------+-------+ 
| ID | Data | Valor | 
+----+------------+-------+ 
| 1 | 2022-11-15 | 100 | 
| 2 | 2022-11-15 | 150 | 
| 3 | 2022-11-16 | 75 | 
| 4 | 2022-11-16 | 120 | 
| 5 | 2022-11-17 | 200 | 
+----+------------+-------+
Agora, vamos usar o GROUP BY para calcular a soma dos valores de vendas para cada data:
SELECT Data, SUM(Valor) AS TotalVendas
FROM vendas
GROUP BY Data;
O resultado será:
+------------+-------------+
| Data       | TotalVendas |
+------------+-------------+
| 2022-11-15 | 250         |
| 2022-11-16 | 195         |
| 2022-11-17 | 200         |
+------------+-------------+


Neste exemplo, usamos o GROUP BY com a coluna "Data" para agrupar as vendas por data e, em seguida, usamos a 
função SUM para calcular a soma dos valores de vendas para cada data agrupada.
