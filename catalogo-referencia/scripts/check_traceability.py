#!/usr/bin/env python3
"""Confere se cada '#### Scenario:' das specs vigentes tem um teste com
@DisplayName("Scenario: <nome>"). Sai com codigo 1 se faltar algum."""
import re, sys, unicodedata
from pathlib import Path

SPECS = Path("openspec/specs")
TESTS = Path("src/test")

def norm(s: str) -> str:
    s = unicodedata.normalize("NFKD", s).encode("ascii", "ignore").decode()
    return re.sub(r"\s+", " ", s).strip().lower()

scenarios = []  # (capability, requisito, cenario)
for spec in sorted(SPECS.glob("**/spec.md")):
    cap = spec.parent.relative_to(SPECS).as_posix()
    req = None
    for line in spec.read_text(encoding="utf-8").splitlines():
        if m := re.match(r"^### Requirement:\s*(.+)$", line):
            req = m.group(1).strip()
        elif m := re.match(r"^#### Scenario:\s*(.+)$", line):
            scenarios.append((cap, req, m.group(1).strip()))

pattern = re.compile(r'@DisplayName\(\s*"Scenario:\s*([^"(]+?)\s*(?:\([^"]*\))?"\s*\)')
covered = {}
for f in TESTS.glob("**/*.java"):
    for m in pattern.finditer(f.read_text(encoding="utf-8")):
        covered.setdefault(norm(m.group(1)), []).append(f.name)

missing = 0
print(f"{'Capability':<18} {'Requisito':<28} {'Cenario':<28} Testes")
for cap, req, sc in scenarios:
    tests = covered.get(norm(sc))
    missing += not tests
    print(f"{cap:<18} {req[:27]:<28} {sc[:27]:<28} "
          f"{', '.join(sorted(set(tests))) if tests else 'FALTANDO'}")
print(f"\n{len(scenarios) - missing}/{len(scenarios)} cenarios com teste")
sys.exit(1 if missing else 0)
