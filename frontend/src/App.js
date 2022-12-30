import { Routes, Route } from "react-router-dom"
import Track from "./components/Track"
import Home from "./components/Home";
import './App.css';

function App() {
  return (
    <div className="App">
      <Routes>
        <Route exact path = "/" element = { <Home/> } />
        <Route path="/track" element={ <Track/> } />
      </Routes>
    </div>
  );
}

export default App;
