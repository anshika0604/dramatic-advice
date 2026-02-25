import { useState } from "react";
import { generateAdvice } from "./api/adviceApi";

export default function App() {
  const [text, setText] = useState("");
  const [advice, setAdvice] = useState("");

  async function handleClick() {
    const res = await generateAdvice(text);
    setAdvice(res.advice);
  }

  return (
    <div style={{padding: 20}}>
      <h1>Dramatic Advice Generator</h1>

      <textarea
        value={text}
        onChange={e => setText(e.target.value)}
        rows={5}
      />

      <br />
      <button onClick={handleClick}>Generate</button>

      <h2>{advice}</h2>
    </div>
  );
}