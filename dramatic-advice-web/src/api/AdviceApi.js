export async function generateAdvice(text) {
  const res = await fetch("http://localhost:8080/api/advice", {
    method: "POST",
    headers: {"Content-Type": "application/json"},
    body: JSON.stringify({ text , mood})
  });

  return res.json();
}