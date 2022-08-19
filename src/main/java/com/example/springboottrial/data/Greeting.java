package com.example.springboottrial.data;

// MEMO: recordは自動的にgetterを生成してくれるが，デフォルトコンストラクタとsetterを用意する手段がおそらく無いのでORM対象には使えない
public record Greeting(long id, String content) {
}
