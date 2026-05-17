# Fluxo Operacional

## Emissao De Frete

Ao emitir um frete, o sistema:

1. valida campos obrigatorios, UF, datas, valores e peso;
2. verifica motorista ativo, CNH valida e ausencia de frete aberto;
3. verifica veiculo disponivel, capacidade e compatibilidade com a CNH;
4. gera numero de frete dentro da transacao JDBC;
5. persiste o frete;
6. solicita o resumo fiscal ao servico externo.

## Maquina De Estados

```text
EMITIDO
  -> SAIDA_CONFIRMADA
  -> EM_TRANSITO
  -> ENTREGUE

EM_TRANSITO
  -> NAO_ENTREGUE

Qualquer estado aberto
  -> CANCELADO
```

Cada transicao permitida cria contexto operacional coerente. Por exemplo:

- ao confirmar saida, o veiculo passa para `EM_VIAGEM`;
- ao finalizar o frete, o veiculo volta para `DISPONIVEL`;
- ocorrencias registram saida, rota, tentativa de entrega, entrega ou excecoes.

## Fechamento Da Operacao

O modulo de relatorios consolida o fluxo em PDFs:

- fretes em aberto;
- romaneio de carga;
- documento individual do frete;
- fretes por cliente;
- ocorrencias por periodo;
- desempenho de motoristas.
